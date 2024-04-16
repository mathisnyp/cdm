{{/* vim: set filetype=mustache: */}}
{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
*/}}
{{- define "neo4j.fullname" -}}
    {{- if .Values.fullnameOverride -}}
        {{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
    {{- else -}}
        {{- if .Values.nameOverride -}}
            {{- $name := default .Chart.Name .Values.nameOverride -}}
            {{- if contains $name .Release.Name -}}
                {{- .Release.Name | trunc 63 | trimSuffix "-" -}}
            {{- else -}}
                {{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
            {{- end -}}
       {{- else -}}
            {{- printf "%s" .Release.Name | trunc 63 | trimSuffix "-" -}}
       {{- end -}}
    {{- end -}}
{{- end -}}
{{/*
Convert a neo4j.conf properties text into valid yaml
*/}}
{{- define "neo4j.configYaml" -}}
  {{- regexReplaceAll "(?m)^([^=]*?)=" ( regexReplaceAllLiteral "\\s*(#|dbms\\.jvm\\.additional).*" . "" )  "${1}: " | trim | replace ": true\n" ": 'true'\n" | replace ": true" ": 'true'\n" | replace ": false\n" ": 'false'\n" | replace ": false" ": 'false'\n"  | replace ": yes\n" ": 'yes'\n" | replace ": yes" ": 'yes'\n" | replace ": no" ": 'no'\n" | replace ": no\n" ": 'no'\n" }}
{{- end -}}

{{- define "neo4j.configJvmAdditionalYaml" -}}
  {{- /* This collects together all dbms.jvm.additional entries */}}
  {{- range ( regexFindAll "(?m)^\\s*(dbms\\.jvm\\.additional=).+" . -1 ) }}{{ trim . | replace "dbms.jvm.additional=" "" | trim | nindent 2 }}{{- end }}
{{- end -}}

{{- define "neo4j.appName" -}}
  {{- $fullname := include "neo4j.fullname" .}}
  {{- .Values.neo4j.name | default $fullname }}
{{- end -}}

{{- define "neo4j.cluster.server_groups" -}}
  {{- $replicaEnabled := index .Values.config "dbms.mode" | default "" | regexMatch "(?i)READ_REPLICA$" }}
  {{- if $replicaEnabled }}
       {{- "read-replicas" }}
  {{ else }}
       {{- "cores" }}
  {{- end -}}
{{- end -}}


{{/* checkNodeSelectorLabels checks if there is any node in the cluster which has nodeSelector labels */}}
{{- define "neo4j.checkNodeSelectorLabels" -}}
    {{- if and (not (empty $.Values.nodeSelector)) (not $.Values.disableLookups) -}}
        {{- $validNodes := 0 -}}
        {{- $numberOfLabelsRequired := len $.Values.nodeSelector -}}
        {{- range $index, $node := (lookup "v1" "Node" .Release.Namespace "").items -}}
            {{- $nodeLabels :=  $node.metadata.labels -}}
            {{- $numberOfLabelsFound := 0 -}}
            {{/* match all the nodeSelector labels with the existing node labels*/}}
            {{- range $name,$value := $.Values.nodeSelector -}}
                {{- if hasKey $nodeLabels $name -}}
                    {{- if eq ($value | toString) (get $nodeLabels $name | toString ) -}}
                        {{- $numberOfLabelsFound = add1 $numberOfLabelsFound -}}
                    {{- end -}}
                {{- end -}}
            {{- end -}}

            {{/* increment valid nodes if the number of labels required are matching with the number of labels found */}}
            {{- if eq $numberOfLabelsFound $numberOfLabelsRequired -}}
                {{- $validNodes = add1 $validNodes -}}
            {{- end -}}
        {{- end -}}

        {{- if eq $validNodes 0 -}}
            {{- fail (print "No node exists in the cluster which has all the below labels (.Values.nodeSelector) \n %s" ($.Values.nodeSelector | join "\n" | toString) ) -}}
        {{- end -}}
    {{- end -}}
{{- end -}}

{{/*
If no name is set in `Values.neo4j.name` sets it to release name and modifies Values.neo4j so that the same name is available everywhere
*/}}
{{- define "neo4j.name" -}}
  {{- if not .Values.neo4j.name }}
    {{- $name := .Release.Name }}
    {{- $ignored := set .Values.neo4j "name" $name }}
  {{- end -}}
  {{- .Values.neo4j.name }}
{{- end -}}

{{/*
If no password is set in `Values.neo4j.password` generates a new random password and modifies Values.neo4j so that the same password is available everywhere
*/}}
{{- define "neo4j.password" -}}
  {{- if and (not .Values.neo4j.passwordFromSecret) (not .Values.neo4j.password) }}
    {{- $password :=  randAlphaNum 14 }}
    {{- $secretName := include "neo4j.appName" . | printf "%s-auth" }}

    {{- $secret := list }}
    {{- if not .Values.disableLookups -}}
    {{- $secret = (lookup "v1" "Secret" .Release.Namespace $secretName) }}
    {{- end -}}

    {{- if $secret }}
      {{- $password = index $secret.data "NEO4J_AUTH" | b64dec | trimPrefix "neo4j/" -}}
    {{- end -}}
    {{- $ignored := set .Values.neo4j "password" $password }}
  {{- end -}}
  {{- if and (.Values.neo4j.password) (not .Values.neo4j.passwordFromSecret)   -}}
  {{- .Values.neo4j.password }}
{{- end -}}
  {{- if .Values.neo4j.passwordFromSecret  -}}
{{- printf "$(kubectl get secret %s -o go-template='{{.data.NEO4J_AUTH | base64decode }}' | cut -d '/' -f2)" .Values.neo4j.passwordFromSecret -}}
{{- end -}}
{{- end -}}

{{- define "neo4j.checkIfClusterIsPresent" -}}

    {{- if not $.Values.disableLookups -}}
        {{- $name := .Values.neo4j.name -}}
        {{- $clusterList := list -}}
        {{- range $index,$pod := (lookup "v1" "Pod" .Release.Namespace "").items -}}
            {{- if eq $name (index $pod.metadata.labels "helm.neo4j.com/neo4j.name" | toString) -}}
                {{- if eq (index $pod.metadata.labels "helm.neo4j.com/dbms.mode" | toString) "CORE" -}}

                    {{- $noOfContainers := len (index $pod.status.containerStatuses) -}}
                    {{- $noOfReadyContainers := 0 -}}

                    {{- range $index,$value := index $pod.status.containerStatuses -}}
                        {{- if $value.ready }}
                            {{- $noOfReadyContainers = add1 $noOfReadyContainers -}}
                        {{- end -}}
                    {{- end -}}

                    {{/* Number of Ready Containers should be equal to the number of containers in the pod */}}
                    {{/* Pod should be in running state */}}
                    {{- if and (eq $noOfReadyContainers $noOfContainers) (eq (index $pod.status.phase | toString) "Running") -}}
                        {{- $clusterList = append $clusterList (index $pod.metadata.name) -}}
                    {{- end -}}

                {{- end -}}
            {{- end -}}
        {{- end -}}

        {{- if lt (len $clusterList) 3 -}}
            {{ fail "Cannot install Read Replica until a cluster of 3 or more cores is formed" }}
        {{- end -}}
    {{- end -}}
{{- end -}}

{{- define "podSpec.checkLoadBalancerParam" }}
{{- $isLoadBalancerValuePresent := required (include "podSpec.loadBalancer.mustBeSetMessage" .) .Values.podSpec.loadbalancer | regexMatch "(?i)include$|(?i)exclude$" -}}
{{- if not $isLoadBalancerValuePresent }}
{{- include "podSpec.loadBalancer.mustBeSetMessage" . | fail -}}
{{- end }}
{{- end }}

{{- define "podSpec.loadBalancer.mustBeSetMessage" }}

Set podSpec.loadbalancer to one of: "include", "exclude".

E.g. by adding `--set podSpec.loadbalancer=include`

{{ end -}}


{{/* Checks if the provided priorityClassName already exists in the cluster or not*/}}
{{- define "neo4j.priorityClassName" -}}
    {{- if not (empty $.Values.podSpec.priorityClassName) -}}

        {{- $priorityClassName := $.Values.podSpec.priorityClassName -}}

        {{- if not $.Values.disableLookups -}}
            {{- $priorityClassName = (lookup "scheduling.k8s.io/v1" "PriorityClass" .Release.Namespace $.Values.podSpec.priorityClassName) -}}
        {{- end -}}

        {{- if empty $priorityClassName -}}
            {{- fail (printf "PriorityClass %s is missing in the cluster" $.Values.podSpec.priorityClassName) -}}
        {{- else -}}
priorityClassName: "{{ .Values.podSpec.priorityClassName }}"
            {{- end -}}
    {{- end -}}
{{- end -}}

{{- define "neo4j.tolerations" -}}
{{/* Add tolerations only if .Values.podSpec.tolerations contains entries */}}
    {{- if . }}
tolerations:
{{ toYaml . }}
    {{- end }}
{{- end -}}

{{- define "neo4j.affinity" -}}
    {{- if or (.Values.podSpec.nodeAffinity) (.Values.podSpec.podAntiAffinity) }}
affinity:
    {{- if and .Values.podSpec.podAntiAffinity }}
        {{- if eq (typeOf .Values.podSpec.podAntiAffinity) "bool" }}
    podAntiAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        - labelSelector:
            matchLabels:
              app: "{{ template "neo4j.name" . }}"
              helm.neo4j.com/pod_category: "neo4j-instance"
          topologyKey: kubernetes.io/hostname
        {{- else }}
    podAntiAffinity: {{ toYaml .Values.podSpec.podAntiAffinity | nindent 6 }}
        {{- end }}
    {{- end }}
    {{- if .Values.podSpec.nodeAffinity }}
    nodeAffinity:
{{ toYaml .Values.podSpec.nodeAffinity | indent 6 }}
    {{- end }}
    {{- end }}
{{- end -}}

{{- define "neo4j.secretName" -}}
    {{- if .Values.neo4j.passwordFromSecret -}}
        {{- if not .Values.disableLookups -}}
            {{- $secret := (lookup "v1" "Secret" .Release.Namespace .Values.neo4j.passwordFromSecret) }}
            {{- $secretExists := $secret | all }}
            {{- if not ( $secretExists ) -}}
                {{ fail (printf "Secret %s configured in 'neo4j.passwordFromSecret' not found" .Values.neo4j.passwordFromSecret) }}
            {{- else if not (hasKey $secret.data "NEO4J_AUTH") -}}
                {{ fail (printf "Secret %s must contain key NEO4J_DATA" .Values.neo4j.passwordFromSecret) }}
            {{/*The secret must start with characters 'neo4j/`*/}}
            {{- else if not (index $secret.data "NEO4J_AUTH" | b64dec | regexFind "^neo4j\\/\\w*") -}}
                {{ fail (printf "Password in secret %s must start with the characters 'neo4j/'" .Values.neo4j.passwordFromSecret) }}
            {{- end -}}
        {{- end -}}
        {{- printf "%s" (tpl .Values.neo4j.passwordFromSecret $) -}}
    {{- else -}}
        {{- include "neo4j.name" . | printf "%s-auth" -}}
    {{- end -}}
{{- end -}}

{{- define "neo4j.passwordWarning" -}}
{{- if and (.Values.neo4j.password) (not .Values.neo4j.passwordFromSecret) -}}
WARNING: Passwords set using 'neo4j.password' will be stored in plain text in the Helm release ConfigMap.
Please consider using 'neo4j.passwordFromSecret' for improved security.
{{- end -}}
{{- end -}}

{{- define "neo4j.topologySpreadConstraints" -}}
{{/* Add tolerations only if .Values.podSpec.topologySpreadConstraints contains entries */}}
    {{- if $.Values.podSpec.topologySpreadConstraints }}
topologySpreadConstraints:
{{ toYaml $.Values.podSpec.topologySpreadConstraints }}
    {{- end }}
{{- end -}}

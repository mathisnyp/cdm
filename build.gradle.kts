group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
   id("org.sonarqube") version "4.4.1.3373"
    jacoco
    java
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}

jacoco {
    toolVersion = "0.8.11"
}



sonar {
   properties {
       property("sonar.sourceEncoding", "UTF-8")
       property("sonar.projectKey", "city-disaster-management-group-7_cdm")
       property("sonar.organization", "city-disaster-management-group-7")
       property("sonar.host.url", "https://sonarcloud.io")
   }
}

subprojects {
   sonarqube {
       properties {
           property("sonar.exclusions", "**/*Generated.java")
       }
   }
}

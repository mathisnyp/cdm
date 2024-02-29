import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm

plugins {
    id("org.siouan.frontend-jdk11") version "8.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

sonarqube {
    properties {
        property ("sonar.sources", "app,assets,components,constants")
    }
}

tasks.register<RunNpm>("jest") {
    script.set("run test-ci")
}

tasks.register<RunNpm>("export-ui") {
    script.set("run export-all")
}

frontend {
    nodeDistributionProvided.set(false)
    nodeVersion.set("20.9.0")
    nodeInstallDirectory.set(project.layout.projectDirectory.dir("node"))
}

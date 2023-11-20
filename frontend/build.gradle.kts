plugins {
    id("org.siouan.frontend-jdk11") version "8.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

frontend {
    nodeDistributionProvided.set(false)
    nodeVersion.set("20.9.0")
    nodeInstallDirectory.set(project.layout.projectDirectory.dir("node"))
}

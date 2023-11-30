pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

//Dependency Versions:


//Project structure:
rootProject.name = "city-disaster-management"

include("services")
include("services:sample-node-service")
include("services:auth")
include("services:auth:service")
include("services:auth:java-api")
include("services:geo-service")
include("backend-for-frontend")
//include("frontend") TODO: include again once finished

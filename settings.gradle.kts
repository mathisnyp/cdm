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


//Project structure:
rootProject.name = "city-disaster-management"

include("services")
include("services:sample-node-service")
include("services:auth")
include("services:auth:service")
include("services:auth:java-api")
include("services:communication-service")
include("services:dublin-open-data-service")
include("services:gen-ai")
include("services:gen-ai:service")
include("services:gen-ai:java-api")
include("services:geo-service")
include("services:incident-service")
include("backend-for-frontend")
include("frontend")

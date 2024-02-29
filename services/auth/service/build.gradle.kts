import com.pswidersk.gradle.python.VenvTask

group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
//    https://github.com/PrzemyslawSwiderski/python-gradle-plugin Look here for docs
//    Couldn't get this plugin working, found work around for now
    java
    id("com.pswidersk.python-plugin") version "2.4.0"
}

pythonPlugin {
    pythonVersion.set("3.9")
}

sonarqube {
    properties {
        property ("sonar.sources", "src")
    }
}

tasks {
    val pipInstall by registering(VenvTask::class) {
        venvExec = "pip"
        args = listOf("install", "--isolated", "-r", "requirements.txt")
    }

    val makeApiSchema by registering(VenvTask::class) {
        args = listOf("./src/main/python/auth/export_openapi.py")
        dependsOn(pipInstall)
    }
    compileJava {
        dependsOn(makeApiSchema)
    }
}

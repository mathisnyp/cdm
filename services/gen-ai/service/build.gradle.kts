group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
//    https://github.com/PrzemyslawSwiderski/python-gradle-plugin Look here for docs
//    Couldn't get this plugin working, found work around for now
    id("com.pswidersk.python-plugin") version "2.4.0"
}

pythonPlugin {
    pythonVersion.set("3.9")
}

tasks.register<Exec>("makeAPISchema") {
    commandLine("python", "export_openapi.py")
}

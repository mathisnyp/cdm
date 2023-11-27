group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    java
    id("org.openapi.generator") version "7.1.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("$projectDir/../service/specs/openapi.json")
    outputDir.set("$buildDir/generated")
    apiPackage.set("ie.tcd.cdm.auth.api")
    invokerPackage.set("ie.tcd.cdm.auth.invoker")
    modelPackage.set("ie.tcd.cdm.auth.model")
}

tasks {
    compileJava {
        dependsOn(openApiGenerate)
    }
}

sourceSets {
    main {
        java {
            srcDir(files("${openApiGenerate.outputDir.get()}/src/main"))
        }
    }
}

dependencies {
    implementation("io.swagger:swagger-annotations:1.6.8")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("io.gsonfire:gson-fire:1.9.0")
    implementation("javax.ws.rs:jsr311-api:1.1.1")
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
}

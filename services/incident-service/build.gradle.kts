plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springframework.cloud.contract") version "3.1.5"
}

group = "ie.tcd.cdm"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("io.micrometer:micrometer-registry-otlp")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.opentelemetry:opentelemetry-api:1.29.0")
    implementation("io.opentelemetry:opentelemetry-context:1.29.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.29.0")
    implementation("io.opentelemetry:opentelemetry-sdk-trace:1.29.0")
    implementation("io.opentelemetry:opentelemetry-sdk-metrics:1.29.0")
    implementation("io.opentelemetry:opentelemetry-sdk-common:1.29.0")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi:1.29.0")
    implementation("io.opentelemetry:opentelemetry-extension-trace-propagators:1.29.0")
    runtimeOnly("io.opentelemetry.instrumentation:opentelemetry-kafka-clients-2.6:1.19.0-alpha")
    implementation("io.opentelemetry:opentelemetry-exporter-common:1.29.0")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:1.29.0")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp-common:1.29.0")
    implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv:1.29.0-alpha")

    implementation("io.opentelemetry.instrumentation:opentelemetry-logback-appender-1.0:1.29.0-alpha")

    implementation("org.neo4j.driver:neo4j-java-driver:4.3.0")

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-zuul")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.3") // Replace with the desired version
    }
}

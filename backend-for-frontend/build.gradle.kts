plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.3"
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
    implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.1.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
//    implementation("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
//    implementation("org.springframework.cloud:spring-cloud-starter-netflix-zuul:2.2.10.RELEASE")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.testng:testng:7.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")
//    testImplementation("org.springframework.boot.test.web.server")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

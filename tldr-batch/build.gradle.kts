plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    kotlin("plugin.spring") version "1.9.20-RC"
}

dependencies {
    implementation(project(":tldr-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

querydsl {
    querydslSourcesDir = "src/main/generated"
}

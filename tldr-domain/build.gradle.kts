import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
//    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

//val queryDslFolder = "src/main/java/run/cd80/tldr/generated"
//
//querydsl {
//    jpa = true
//    querydslSourcesDir = queryDslFolder
//}
//
//sourceSets {
//    main {
//        java {
//            srcDir(queryDslFolder)
//        }
//    }
//}
//
//tasks.compileQuerydsl {
//    options.annotationProcessorPath = configurations["querydsl"]
//}
//
//configurations {
//    compileOnly {
//        extendsFrom(configurations.annotationProcessor.get())
//    }
//
//    querydsl.extendsFrom(compileClasspath)
//}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.diffplug.spotless") version "6.22.0"
    kotlin("plugin.spring") version "1.9.20-RC"
    kotlin("jvm") version "1.9.20-RC"
}

group = "run.cd80"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    group = "run.cd80.tldr"
    version = "0.0.1"
    repositories { mavenCentral() }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "com.diffplug.spotless")

    dependencies {
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
        implementation(kotlin("stdlib-jdk8"))
        runtimeOnly("org.apache.logging.log4j:log4j-api-kotlin:1.3.0")
        testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
        testImplementation(kotlin("test-junit5"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "${JavaVersion.VERSION_17}"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

spotless {
    java {
        target("**/*.java")
        targetExclude("$buildDir/**/*.java", "bin/**/*.java")
        googleJavaFormat("1.11.0")
    }

    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt", "bin/**/*.kt")
        ktlint("0.49.1")
                .editorConfigOverride(mapOf(
                        "charset" to "utf-8",
                        "end_of_line" to "lf",
                        "insert_final_newline" to true,
                        "indent_style" to "space",
                        "indent_size" to 4,
                        "trim_trailing_whitespace" to true,
                        "ij_kotlin_allow_trailing_comma" to true,
                        "ij_kotlin_allow_trailing_comma_on_call_site" to true,
                ))
    }
}

task("addPreCommitGitHookOnBuild") {
    println("⚈ ⚈ ⚈ Running Add Pre Commit Git Hook Script on Build ⚈ ⚈ ⚈")
    exec {
        commandLine("cp", "./.scripts/pre-commit", "./.git/hooks")
    }
    println("✅ Added Pre Commit Git Hook Script.")
}

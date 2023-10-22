dependencies {
    implementation("com.github.kittinunf.fuel:fuel:3.0.0-alpha1")
    implementation("org.jsoup:jsoup:1.16.1")

    implementation("io.ktor:ktor-client:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-jackson:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-xml:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-cbor:2.3.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

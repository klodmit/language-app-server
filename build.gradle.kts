val kotlin_version = "1.9.0"
val logback_version = "1.2.11"
val postgres_version = "42.3.1"
val h2_version = "2.1.214"
val exposed_version = "0.38.2"

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.2")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.2")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.2")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:2.3.2")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

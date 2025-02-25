plugins {
    kotlin("jvm") version "1.9.23"
    id("maven-publish")
}

group = "noh.jinil.utils"
version = "1.2.0"

apply("publish.gradle.kts")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    implementation("com.google.code.gson:gson:2.10")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
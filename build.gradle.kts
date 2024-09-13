plugins {
    kotlin("jvm") version "1.9.23"
}

group = "noh.jinil.utils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
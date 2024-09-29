import java.util.Properties

val secretPropsFile = rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    // Read local.properties file first if it exists
    val properties = Properties()
    secretPropsFile.inputStream().use { inputStream ->
        properties.load(inputStream)
    }

    extra["nexusUsername"] = properties.getProperty("nexusUsername")
    extra["nexusPassword"] = properties.getProperty("nexusPassword")
} else {
    extra["nexusUsername"] = System.getenv("NEXUS_USERNAME")
    extra["nexusPassword"] = System.getenv("NEXUS_PASSWORD")
}

extra["PUBLISH_GROUP_ID"] = "noh.jinil.utils"
extra["PUBLISH_VERSION"] = "1.1.3"
extra["PUBLISH_ARTIFACT_ID"] = "SmartLog"

val sourceSets = project.extensions.getByType<JavaPluginExtension>().sourceSets

// 소스 코드 JAR 생성 task 정의
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

// Javadoc JAR 생성 task 정의 (선택적)
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.named("javadoc", Javadoc::class).get().destinationDir)
}

configure<PublishingExtension> {
    repositories {
        maven {
            name = "gurunun"
            url = uri("https://nexus.gurunun.me/repository/maven-hosted/")
            credentials {
                username = extra["nexusUsername"] as? String
                password = extra["nexusPassword"] as? String
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = extra["PUBLISH_GROUP_ID"] as? String
            artifactId = extra["PUBLISH_ARTIFACT_ID"] as? String
            version = extra["PUBLISH_VERSION"] as? String

            artifact(sourcesJar.get())
            artifact(javadocJar.get())
        }
    }
}
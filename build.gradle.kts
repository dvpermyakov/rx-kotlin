plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.72"
}

repositories {
    jcenter()
}

sourceSets.main {
    java.srcDirs("src/main/java")
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("junit:junit:4.13")
    testImplementation("io.mockk:mockk:1.10.0")
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            group = "com.dvpermyakov"
            version = "0.1"
            artifactId = "rx-kotlin"

            from(components["java"])
            artifact(sourcesJar)
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}
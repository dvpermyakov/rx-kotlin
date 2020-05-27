import java.util.Date

plugins {
    kotlin("jvm") version "1.3.72"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
}

buildscript {
    repositories {
        dependencies {
            classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
        }
    }
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

val currentVersion = "0.1.3"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            group = "com.dvpermyakov"
            version = currentVersion
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

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = true

    setPublications("mavenJava")

    pkg.apply {
        repo = "rx-kotlin"
        name = "core"
        vcsUrl = "https://github.com/dvpermyakov/rx-kotlin.git"
        setLicenses("MIT")

        version.apply {
            name = currentVersion
            desc = ""
            released = Date().toString()
            vcsTag = currentVersion
        }
    }
}
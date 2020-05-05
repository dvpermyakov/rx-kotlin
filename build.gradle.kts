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
}

group = "com.dvpermyakov"
version = "0.1"

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}
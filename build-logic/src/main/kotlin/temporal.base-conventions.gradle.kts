plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.gradleup.nmcp")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

dependencies {
    compileOnly(libs.jetbrains.annotations)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.junit)
    testRuntimeOnly(libs.junit.launcher)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }

    test {
        useJUnitPlatform()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    withSourcesJar()
    withJavadocJar()
}

signing {
    sign(publishing.publications)
    useGpgCmd()
}

publishing {
    publications.create<MavenPublication>("mavenJava") {
        groupId = rootProject.group as String
        artifactId = project.name
        version = rootProject.version as String

        from(components["java"])
        pom {
            name.set("ksTemporal")
            description.set("A lightweight library for easy time manipulation, including formatting.")
            url.set("https://github.com/Xkyrell/ksTemporal")
            scm {
                url.set("https://github.com/Xkyrell/ksTemporal")
                connection.set("scm:git:https://github.com/Xkyrell/ksTemporal.git")
                developerConnection.set("scm:git:ssh://git@github.com/Xkyrell/ksTemporal.git")
            }
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/license/mit")
                }
            }
            developers {
                developer {
                    id.set("xkyrell")
                    name.set("Xkyrell")
                }
            }
        }
    }
}

rootProject.name = "ksTemporal"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        id("com.gradleup.nmcp") version "1.6.2"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("temporal-core", "temporal-serializer")

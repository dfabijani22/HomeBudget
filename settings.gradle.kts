pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Kotlin (za cijeli projekt)
        id("org.jetbrains.kotlin.android") version "1.9.22"

        // Hilt (za cijeli projekt)
        id("com.google.dagger.hilt.android") version "2.51.1"

    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HomeBudgetApp"

include(":app")
include(":core")
include(":feature_home_api")
include(":feature_home_impl")

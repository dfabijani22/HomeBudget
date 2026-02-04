pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins{
        id("com.google.dagger.hilt.android") version "2.48"
    }
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

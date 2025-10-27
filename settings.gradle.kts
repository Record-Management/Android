pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Android"
include(":app")

include(
    "core:domain",
    "core:data",
    "core:model",
    "core:designsystem",
    "core:ui",
    "core:navigation"
)

include(
    "feature:main",
    "feature:login",
    "feature:onboarding",
    "feature:home",
    "feature:daily",
    "feature:exercise",
    "feature:habit",
    "feature:setting",
    "feature:notification"
)

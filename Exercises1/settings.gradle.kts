pluginManagement {
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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Exercises 1"
include(":01_2nums")
include(":02_CirclePerimeter")
include(":03_toBinary")
include(":04_countInString")
include(":05_reverseString")
include(":06_ArrayMulti")
include(":07_CountEvenAndOdd")
include(":08_pyramid")
include(":09_matrixAddition")
include(":10_calcAverage")

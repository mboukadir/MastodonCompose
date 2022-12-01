plugins {
    id("kotlin-multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.diffplug.spotless") version "6.11.0"
    id("kotlin-parcelize")
    id("com.google.osdetector") version "1.7.1"
}
spotless {
    kotlin {
        target("src/*/kotlin/**/*.kt")
        ktlint("0.43.2")
        licenseHeaderFile(rootProject.file("copyright.kt"))
    }
}

val targetSDKVersion: Int by rootProject.extra
val minSDKVersion: Int by rootProject.extra
val compileSDKVersion: Int by rootProject.extra

android {
    namespace = "social.androiddev.common"
    compileSdk = compileSDKVersion

    defaultConfig {
        minSdk = minSDKVersion
        targetSdk = targetSDKVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

kotlin {
    jvm("desktop")
    android()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(projects.data.network)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                api(libs.com.arkivanov.decompose)
                api(libs.com.arkivanov.decompose.extensions.compose.jetbrains)
            }
        }

        named("androidMain") {
            dependencies {
                // Workaround for https://github.com/JetBrains/compose-jb/issues/2340
                implementation(libs.androidx.compose.foundation)
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
                //https://stackoverflow.com/questions/73187027/use-javafx-in-kotlin-multiplatform
                // As JavaFX have platform-specific dependencies, we need to add them manually
                val fxSuffix = when (osdetector.classifier) {
                    "linux-x86_64" -> "linux"
                    "linux-aarch_64" -> "linux-aarch64"
                    "windows-x86_64" -> "win"
                    "osx-x86_64" -> "mac"
                    "osx-aarch_64" -> "mac-aarch64"
                    else -> throw IllegalStateException("Unknown OS: ${osdetector.classifier}")
                }

                // Replace "compileOnly" with "implementation" for a non-library project
                implementation("org.openjfx:javafx-base:18.0.2:${fxSuffix}")
                implementation("org.openjfx:javafx-graphics:18.0.2:${fxSuffix}")
                implementation("org.openjfx:javafx-controls:18.0.2:${fxSuffix}")
                implementation("org.openjfx:javafx-web:18.0.2:${fxSuffix}")
                implementation("org.openjfx:javafx-swing:18.0.2:${fxSuffix}")
                implementation("org.openjfx:javafx-media:18.0.2:${fxSuffix}")
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

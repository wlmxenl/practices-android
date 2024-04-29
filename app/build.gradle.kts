plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.demo.archive"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.demo.archive"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaCompiler.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaCompiler.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaCompiler.get()
    }

    viewBinding {
        enable = true
    }

    dataBinding {
        enable = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // https://developer.android.google.cn/jetpack/androidx/releases/compose-kotlin?hl=zh-cn#kts
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:custom-view"))


    val composeBom = platform("androidx.compose:compose-bom:2024.04.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Choose one of the following:
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // or Material Design 2
    implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")

    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "com.gitlab.api"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaCompiler.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaCompiler.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaCompiler.get()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    publishing {
        singleVariant("release") {
            withJavadocJar()
            withSourcesJar()
        }
    }
}

dependencies {
    compileOnly(libs.androidx.core.ktx)
    compileOnly(libs.androidx.appcompat)

    compileOnly(libs.utilcode)
    compileOnly(libs.okhttp)
    compileOnly(libs.net)

    testImplementation(libs.test.junit4)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.wlmxenl"
                artifactId = "openapi-gitlab"
                version = "1.0"
                from(components["release"])
            }
        }
    }
}

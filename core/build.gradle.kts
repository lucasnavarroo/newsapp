import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 31
        targetSdk = 36

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "NEWS_API_KEY", "\"${getLocalProperty("NEWS_API_KEY")}\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "NEWS_API_KEY", "\"${getLocalProperty("NEWS_API_KEY")}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.biometric)
}

fun getLocalProperty(key: String): String {
    val properties = Properties()
    properties.load(rootProject.file("local.properties").inputStream())
    return properties.getProperty(key, "")
}

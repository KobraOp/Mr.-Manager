import java.util.Properties
import java.io.FileInputStream

// Load properties safely
val secretProperties = Properties()
val secretPropertiesFile = rootProject.file("secrets.properties")

if (secretPropertiesFile.exists()) {
    FileInputStream(secretPropertiesFile).use { secretProperties.load(it) }
} else {
    println("⚠️ Warning: secrets.properties file not found!")
}

// Debugging output (shows in Gradle console)
println("🔹 Loaded WEB_CLIENT_ID: ${secretProperties["WEB_CLIENT_ID"] ?: "NOT FOUND"}")

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.android.kobra"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.android.kobra"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEB_CLIENT_ID", "\"${secretProperties["WEB_CLIENT_ID"] ?: ""}\"")
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        buildFeatures {
            buildConfig = true // ✅ Ensures `BuildConfig` is generated
            dataBinding = true
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

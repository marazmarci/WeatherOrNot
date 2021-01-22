plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "dev.maraz.weatherornot"
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // Kotlin
    implementation(kotlin("stdlib", ext("kotlinVersion")))

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    val ktxLifecycleVersion = "2.2.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktxLifecycleVersion")

    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // Material Components
    implementation("com.google.android.material:material:1.2.1")

    // Testing
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

fun ext(name: String) = rootProject.extra[name] as String
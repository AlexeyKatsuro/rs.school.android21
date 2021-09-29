plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.5.31"
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.alexeykatsuro.tast5_network"
        minSdk = 23
        targetSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        // Disabling coroutine's ExperimentalCoroutinesApi warning
        // Disabling serialization's ExperimentalSerializationApi warning
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.TheAnnotationYouWantToDisable"
        )

    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val nav_version = "2.3.5"

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.paging:paging-runtime:3.0.1")

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0-RC")

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation("io.coil-kt:coil:1.3.2")
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.0-beta01")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
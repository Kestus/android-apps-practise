import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)

    // Dagger Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.app020_paging"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.app020_paging"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", gradleLocalProperties(rootDir, providers).getProperty("api_key"))
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    val retrofit_version = "2.11.0"

    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    // GSON converter
    implementation("com.google.code.gson:gson:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    // optional RxJava3 support
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofit_version")

    // Paging
    val paging_version = "3.3.0"
    implementation("androidx.paging:paging-runtime:$paging_version")
    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")

    // Dagger Hilt
    val dagger_version = "2.51.1"
    implementation("com.google.dagger:hilt-android:$dagger_version")
    annotationProcessor("com.google.dagger:hilt-compiler:$dagger_version")

    // Glide
    val glide_version = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glide_version")
    annotationProcessor("com.github.bumptech.glide:compiler:$glide_version")

    // Life Cycle
    val lifecycle_version = "2.8.2"
    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
//     ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
}
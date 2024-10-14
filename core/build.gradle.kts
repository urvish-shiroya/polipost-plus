plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "com.polipost.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.gson)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.datastore)

    api(libs.androidx.database.room)
    api(libs.androidx.database.room.runtime)
    ksp(libs.androidx.database.room.ksp)

    api(project.dependencies.platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.core.coroutines)
    api(libs.koin.android)

    api(libs.retrofit)
    api(libs.retrofit.converter)

    api(libs.glide)
    annotationProcessor(libs.glide.compiler)

    api(libs.sdp)
    api(libs.lottie)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
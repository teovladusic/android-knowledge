plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.puzzle_agency.androidknowledge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.puzzle_agency.androidknowledge"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.ui)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    // Dagger Hilt
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.bundles.dagger.hilt)

    // compose lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    // material
    implementation(libs.material3)

    // icons
    implementation(libs.androidx.material.icons.extended)

    // retrofit 2
    implementation(libs.bundles.retrofit)

    // gson
    implementation(libs.converter.gson)

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // datastore
    implementation(libs.androidx.datastore.preferences)

    // coil images
    implementation(libs.coil.compose)

    // google sign in
    implementation(libs.play.services.auth)

    // camera x
    implementation(libs.bundles.camera)

    // accompanist permissions
    implementation(libs.accompanist.permissions)

    // firebase
    implementation(libs.firebase.messaging.ktx)

    // widget
    implementation(libs.bundles.glance)

    // work manager
    implementation(libs.work.runtime.ktx)

    // media 3 exoplayer
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)

    // tensorflow light
    implementation(libs.tensorflow.lite.task.vision.play.services)
    implementation(libs.tensorflow.lite.gpu)
}

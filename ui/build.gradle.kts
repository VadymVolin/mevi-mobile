plugins {
    alias(libs.plugins.android.library.plugin)
    alias(libs.plugins.android.kotlin.plugin)
}

android {
    namespace = "com.mevi.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"688998092838-bo9ln4vmu1c1g0ueousoj74b5g18m010.apps.googleusercontent.com\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDefault = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.coroutines.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Splash Api
    implementation (libs.androidx.core.splashscreen)
    //Icons
    implementation (libs.androidx.material.icons.extended)
    //Coil
    implementation(libs.coil.compose)
    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx) // TODO: For testing only, remove when logout feature is ready

    // credentials manager
    implementation(libs.androidx.credential.manager)
    implementation(libs.androidx.credential.manager.play)
    implementation(libs.google.id.library)

    //di
    implementation(libs.koin.androidx.compose)
    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.viewmodel.compose)

    //modules
    implementation(project(":domain"))
    implementation(project(":common"))

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
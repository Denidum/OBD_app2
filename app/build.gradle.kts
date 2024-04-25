plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.chaquo.python")
}

android {
    namespace = "com.example.obd_app2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.obd_app2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }

        flavorDimensions += "pyVersion"
        productFlavors {
            create("py38") { dimension = "pyVersion" }
        }
    }
    chaquopy {
        defaultConfig {
                version = "3.8"
                buildPython("C:/Users/Tanya/AppData/Local/Programs/Python/Python38/python.exe")
            pip {
                // A requirement specifier, with or without a version number:
                install("mysql-connector-python")

            }
        }
        productFlavors {
            getByName("py38") { version = "3.8" }
        }
        sourceSets {
            getByName("main") {
                srcDir("src/main/python")
            }
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
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")
}
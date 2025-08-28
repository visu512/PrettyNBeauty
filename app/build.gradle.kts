//import io.netty.util.ReferenceCountUtil.release
//
////import io.grpc.internal.SharedResourceHolder.release
////
//////plugins {
//////    alias(libs.plugins.android.application)
//////    alias(libs.plugins.jetbrains.kotlin.android)
//////    alias(libs.plugins.google.gms.google.services)
//////    id("kotlin-parcelize")
//////    kotlin("plugin.serialization") version "1.9.0" apply false
//////}
//////
//////android {
//////    namespace = "com.beauty.parler"
//////    compileSdk = 35
//////
//////    defaultConfig {
//////        applicationId = "com.beauty.parler"
//////        minSdk = 24
//////        targetSdk = 35
//////        versionCode = 4
//////        versionName = "1.2"
//////
//////        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//////        vectorDrawables {
//////            useSupportLibrary = true
//////        }
//////    }
//////
//////    buildTypes {
//////        release {
//////            // Enable code shrink + optimization
//////            isMinifyEnabled = false
//////            isShrinkResources = false
//////
//////            proguardFiles(
//////                getDefaultProguardFile("proguard-android-optimize.txt"),
//////                "proguard-rules.pro"
//////            )
//////
//////            // Native debug symbols (for Play Console crash reports)
//////            ndk {
//////                debugSymbolLevel = "FULL"
//////            }
//////        }
//////
//////        debug {
//////            // Debug build fast compilation ke liye optimized nahi hota
//////            isMinifyEnabled = false
//////            isShrinkResources = false
//////        }
//////    }
//////
//////
//////    compileOptions {
//////        sourceCompatibility = JavaVersion.VERSION_1_8
//////        targetCompatibility = JavaVersion.VERSION_1_8
//////    }
//////    kotlinOptions {
//////        jvmTarget = "1.8"
//////    }
//////    buildFeatures {
//////        compose = true
//////    }
//////    composeOptions {
//////        kotlinCompilerExtensionVersion = "1.5.1"
//////    }
//////    packaging {
//////        resources {
//////            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//////        }
//////    }
//////}
//////
//////dependencies {
//////    // Core & Lifecycle
//////    implementation("androidx.core:core-ktx:1.15.0")
//////    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//////    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//////    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//////
//////    // Compose BOM
//////    implementation(platform(libs.androidx.compose.bom))
//////    implementation("androidx.compose.ui:ui")
//////    implementation("androidx.compose.ui:ui-graphics")
//////    implementation("androidx.compose.ui:ui-tooling-preview")
//////    implementation("androidx.compose.material3:material3")
//////    implementation("androidx.compose.material:material-icons-extended")
//////
//////
//////   // auth
//////    implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
//////    implementation platform("com.google.firebase:firebase-bom:32.7.0")
//////
//////
//////    implementation("androidx.compose.material:material")
//////
//////    // Activity Compose
//////    implementation("androidx.activity:activity-compose:1.8.0")
//////
//////    // Coil
//////    implementation("io.coil-kt:coil-compose:2.4.0")
//////
//////    // Navigation
//////    implementation("androidx.navigation:navigation-compose:2.8.0")
//////    implementation(libs.androidx.runtime.android)
//////
//////    // For Gson serialization
//////    implementation("com.google.code.gson:gson:2.9.0")
//////
//////    // For ViewModel and StateFlow
//////    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
//////    implementation("androidx.compose.runtime:runtime-livedata:1.3.0")
//////
//////    // Firebase Firestore
//////    implementation(libs.firebase.firestore)
//////
//////    // DataStore
//////    implementation("androidx.datastore:datastore-preferences:1.0.0")
//////
//////    // Kotlin Serialization
//////    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
//////
//////    // Protobuf
//////    implementation("com.google.protobuf:protobuf-javalite:3.24.0")
//////
//////    implementation ("io.coil-kt:coil-compose:2.4.0")
//////    implementation ("io.coil-kt:coil:2.4.0")
//////
//////    // AndroidX & Material
//////    implementation(libs.androidx.appcompat)
//////    implementation(libs.material)
//////    implementation(libs.androidx.activity)
//////    implementation(libs.androidx.constraintlayout)
////////    implementation(libs.firebase.auth)
//////    implementation(libs.androidx.credentials)
//////    implementation(libs.androidx.credentials.play.services.auth)
//////    implementation(libs.googleid)
//////
//////    // Testing
//////    testImplementation(libs.junit)
//////    androidTestImplementation(libs.androidx.junit)
//////    androidTestImplementation(libs.androidx.espresso.core)
//////    androidTestImplementation(platform(libs.androidx.compose.bom))
//////    androidTestImplementation(libs.androidx.ui.test.junit4)
//////    debugImplementation(libs.androidx.ui.tooling)
//////    debugImplementation(libs.androidx.ui.test.manifest)
//////
//////    implementation(kotlin("script-runtime"))
//////}
////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////plugins {
//////    id("com.android.application")
//////    id("org.jetbrains.kotlin.android")
//////    id("com.google.gms.google-services")
//////    id("kotlin-parcelize")
//////}
//////
//////android {
//////    namespace = "com.beauty.parler"
//////    compileSdk = 35
//////
//////    defaultConfig {
//////        applicationId = "com.beauty.parler"
//////        minSdk = 24
//////        targetSdk = 35
//////        versionCode = 4
//////        versionName = "1.2"
//////
//////        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//////        vectorDrawables { useSupportLibrary = true }
//////    }
//////
//////    buildTypes {
//////        release {
//////            isMinifyEnabled = false
//////            isShrinkResources = false
//////            proguardFiles(
//////                getDefaultProguardFile("proguard-android-optimize.txt"),
//////                "proguard-rules.pro"
//////            )
//////            ndk { debugSymbolLevel = "FULL" }
//////        }
//////        debug {
//////            isMinifyEnabled = false
//////            isShrinkResources = false
//////        }
//////    }
//////
//////    compileOptions {
//////        sourceCompatibility = JavaVersion.VERSION_1_8
//////        targetCompatibility = JavaVersion.VERSION_1_8
//////    }
//////    kotlinOptions { jvmTarget = "1.8" }
//////
//////    buildFeatures { compose = true }
//////    composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
//////
//////    packaging {
//////        resources {
//////            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//////        }
//////    }
//////}
//////
//////dependencies {
//////    // Core & Lifecycle
//////    implementation("androidx.core:core-ktx:1.15.0")
//////    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//////    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//////
//////    // Compose BOM
//////    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
//////    implementation("androidx.compose.ui:ui")
//////    implementation("androidx.compose.ui:ui-graphics")
//////    implementation("androidx.compose.ui:ui-tooling-preview")
//////    implementation("androidx.compose.material3:material3")
//////    implementation("androidx.compose.material:material-icons-extended")
//////
//////    // Firebase (Auth + Firestore)
//////    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
//////    implementation("com.google.firebase:firebase-auth-ktx")
//////    implementation("com.google.firebase:firebase-firestore-ktx")
//////
//////    // Navigation
//////    implementation("androidx.navigation:navigation-compose:2.8.0")
//////
//////    // DataStore
//////    implementation("androidx.datastore:datastore-preferences:1.0.0")
//////
//////    // Coil
//////    implementation("io.coil-kt:coil-compose:2.4.0")
//////
//////    // Other deps
//////    implementation("com.google.code.gson:gson:2.9.0")
//////    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
//////    implementation("com.google.protobuf:protobuf-javalite:3.24.0")
//////
//////    // AndroidX & Material
//////    implementation("androidx.appcompat:appcompat:1.6.1")
//////    implementation("com.google.android.material:material:1.9.0")
//////    implementation("androidx.activity:activity-compose:1.8.0")
//////    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//////
//////    // Credentials API (for Google Sign-in if needed)
//////    implementation("androidx.credentials:credentials:1.2.0")
//////    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
//////    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
//////
//////    // google play integrity
//////    implementation ("com.google.android.play:integrity:1.2.0")
//////
//////    implementation ("com.google.android.gms:play-services-auth:20.7.0")
//////
//////
//////    // Testing
//////    testImplementation("junit:junit:4.13.2")
//////    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//////    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//////    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
//////    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//////    debugImplementation("androidx.compose.ui:ui-tooling")
//////    debugImplementation("androidx.compose.ui:ui-test-manifest")
//////}
////
////
////
////
////plugins {
////    id("com.android.application")
////    id("org.jetbrains.kotlin.android")
////    id("com.google.gms.google-services")
////    id("kotlin-parcelize")
////}
////
////android {
////    namespace = "com.beauty.parler"
////    compileSdk = 35
////
////    defaultConfig {
////        applicationId = "com.beauty.parler"
////        minSdk = 24
////        targetSdk = 35
////        versionCode = 4
////        versionName = "1.2"
////
////        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
////        vectorDrawables { useSupportLibrary = true }
////    }
////
////    signingConfigs {
////        release {
////            storeFile file("C:\\Users\\vilak\\Download\\prettynbeautynew.jks")
////            storePassword "Beauty@123"
////            keyAlias "beautykey"
////            keyPassword "Beauty@123"
////        }
////    }
////
////    buildTypes {
////        release {
////            signingConfig signingConfigs.release
////                    isMinifyEnabled false
////            isShrinkResources false
////            proguardFiles(
////                getDefaultProguardFile("proguard-android-optimize.txt"),
////                "proguard-rules.pro"
////            )
////            ndk {
////                debugSymbolLevel = "FULL"
////            }
////        }
////
////        debug {
////            // debug config agar chahiye
////        }
////    }
////
////
////    compileOptions {
////        sourceCompatibility = JavaVersion.VERSION_1_8
////        targetCompatibility = JavaVersion.VERSION_1_8
////    }
////    kotlinOptions { jvmTarget = "1.8" }
////
////    buildFeatures { compose = true }
////    composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
////
////    packaging {
////        resources {
////            excludes += "/META-INF/{AL2.0,LGPL2.1}"
////        }
////    }
////}
////
////
////dependencies {
////    // Core & Lifecycle
////    implementation("androidx.core:core-ktx:1.15.0")
////    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
////    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
////
////    // Compose BOM
////    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
////    implementation("androidx.compose.ui:ui")
////    implementation("androidx.compose.ui:ui-graphics")
////    implementation("androidx.compose.ui:ui-tooling-preview")
////    implementation("androidx.compose.material3:material3")
////    implementation("androidx.compose.material:material-icons-extended")
////
////    // Firebase (Auth + Firestore)
////    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
////    implementation("com.google.firebase:firebase-auth-ktx")
////    implementation("com.google.firebase:firebase-firestore-ktx")
////
////    // Navigation
////    implementation("androidx.navigation:navigation-compose:2.8.0")
////
////    // DataStore
////    implementation("androidx.datastore:datastore-preferences:1.0.0")
////
////    // Coil
////    implementation("io.coil-kt:coil-compose:2.4.0")
////
////    // Other deps
////    implementation("com.google.code.gson:gson:2.9.0")
////    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
////    implementation("com.google.protobuf:protobuf-javalite:3.24.0")
////
////    // AndroidX & Material
////    implementation("androidx.appcompat:appcompat:1.6.1")
////    implementation("com.google.android.material:material:1.9.0")
////    implementation("androidx.activity:activity-compose:1.8.0")
////    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
////
////    // Credentials API (for Google Sign-in if needed)
////    implementation("androidx.credentials:credentials:1.2.0")
////    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
////    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
////
////    // google play integrity
////    implementation ("com.google.android.play:integrity:1.2.0")
////
////    implementation ("com.google.android.gms:play-services-auth:20.7.0")
////
////
////    // Testing
////    testImplementation("junit:junit:4.13.2")
////    androidTestImplementation("androidx.test.ext:junit:1.1.5")
////    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
////    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
////    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
////    debugImplementation("androidx.compose.ui:ui-tooling")
////    debugImplementation("androidx.compose.ui:ui-test-manifest")
////}
////
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//plugins {
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
//    id("com.google.gms.google-services")
//    id("kotlin-parcelize")
//}
//
//android {
//    namespace = "com.beauty.parler"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.beauty.parler"
//        minSdk = 24
//        targetSdk = 35
//        versionCode = 4
//        versionName = "1.2"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables { useSupportLibrary = true }
//    }
//
//    signingConfigs {
//        release {
//            storeFile = file("C:\\Users\\vilak\\Download\\prettynbeautynew.jks")
//            isStorePassword = "Beauty@123"
//            keyAlias = "beautykey"
//            keyPassword = "Beauty@123"
//        }
//    }
//
//    buildTypes {
//        release {
//            signingConfig = signingConfigs.getByName("release")
//            isMinifyEnabled = false
//            isShrinkResources = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            ndk {
//                debugSymbolLevel = "FULL"
//            }
//        }
//
//        debug {
//            signingConfig = signingConfigs.getByName("release") // optional: debug भी same JKS से sign होगा
//            isMinifyEnabled = false
//            isShrinkResources = false
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions { jvmTarget = "1.8" }
//
//    buildFeatures { compose = true }
//    composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
//
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//    // Core & Lifecycle
//    implementation("androidx.core:core-ktx:1.15.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//
//    // Compose BOM
//    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.material:material-icons-extended")
//
//    // Firebase (Auth + Firestore)
//    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
//    implementation("com.google.firebase:firebase-auth-ktx")
//    implementation("com.google.firebase:firebase-firestore-ktx")
//
//    // Navigation
//    implementation("androidx.navigation:navigation-compose:2.8.0")
//
//    // DataStore
//    implementation("androidx.datastore:datastore-preferences:1.0.0")
//
//    // Coil
//    implementation("io.coil-kt:coil-compose:2.4.0")
//
//    // Other deps
//    implementation("com.google.code.gson:gson:2.9.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
//    implementation("com.google.protobuf:protobuf-javalite:3.24.0")
//
//    // AndroidX & Material
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//
//    // Credentials API (for Google Sign-in if needed)
//    implementation("androidx.credentials:credentials:1.2.0")
//    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
//    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
//
//    // google play integrity
//    implementation("com.google.android.play:integrity:1.2.0")
//
//    implementation("com.google.android.gms:play-services-auth:20.7.0")
//
//    // Testing
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//}



















plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.beauty.parler"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.beauty.parler"
        minSdk = 24
        targetSdk = 35
        versionCode = 4
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\vilak\\Download\\prettynbeautynew.jks")
            storePassword = "Beauty@123"
            keyAlias = "beautykey"
            keyPassword = "Beauty@123"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                debugSymbolLevel = "FULL"
            }
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("release") //  debug same JKS
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions { jvmTarget = "1.8" }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core & Lifecycle
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Firebase (Auth + Firestore)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Other deps
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
    implementation("com.google.protobuf:protobuf-javalite:3.24.0")

    // AndroidX & Material
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Credentials API (for Google Sign-in if needed)
    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    // google play integrity
    implementation("com.google.android.play:integrity:1.2.0")

    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

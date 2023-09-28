@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.bsuir.zhlobin.uniquekurankouyauhen.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bsuir.zhlobin.uniquekurankouyauhen.myapplication"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.1.2"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    annotationProcessor("androidx.hilt:hilt-compiler:1.0.0")
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation ("com.google.dagger:hilt-android:2.44.2")
    ksp ("com.google.dagger:hilt-android-compiler:2.28-alpha")
   // implementation(libs.compose.destinations)
    // Navigation
   // implementation ("androidx.navigation:navigation-fragment-ktx:2.3.0-beta01")
  //  implementation ("androidx.navigation:navigation-ui-ktx:2.3.0-beta01")
    // Dynamic Feature Module Support
  //  implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.3.0-beta01")
    // Testing Navigation
   // androidTestImplementation ("androidx.navigation:navigation-testing:2.3.0-beta01")

    //TryByHabrGoogle
    //implementation("androidx.navigation:navigation-compose:2.4.0-beta02")
    //TryBySomeYouTube
    val nav_version = "2.7.3"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //debugImplementation(libs.compose.tooling) // androidx.compose.ui:ui-tooling
}

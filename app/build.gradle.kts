plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinParcelize)
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.mobilefolk.test"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = 1
        versionName = "1.0.0"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        viewBinding = true
    }
}

dependencies {
    // Timber
    implementation(Dependencies.timber)
    // Coroutines
    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutineAndroid)
    // Koin
    implementation(Dependencies.koinCore)
    implementation(Dependencies.koinAndroid)
    // FP
    implementation(Dependencies.arrow)
    // Android
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxAppCompat)
    implementation(Dependencies.googleMaterialDesign)
    implementation(Dependencies.androidxConstraintLayout)
    implementation(Dependencies.androidxNavigationFragmentKtx)
    implementation(Dependencies.androidxNavigationUiKtx)
    implementation(Dependencies.androidxLivedata)
    implementation(Dependencies.androidxViewModel)
    // Webkit
    implementation(Dependencies.webkit)
    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.jackson)
    implementation(Dependencies.jacksonKotlin)
    // ViewPager2
    implementation(Dependencies.viewpager2)
    // Glide
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glideComplier)
    // Shimmer
    implementation(Dependencies.shimmer)
    // PrettyTime
    implementation(Dependencies.prettyTime)
    // Swipe Layout
    implementation(Dependencies.swipeRefreshLayout)
    // Other
    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.androidxTestJUnit)
    androidTestImplementation(Dependencies.androidxTestEspresso)
}
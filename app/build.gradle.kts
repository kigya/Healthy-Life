plugins {
    id(Plugins.application)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.safeargas)
    id(Plugins.hilt)
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")

}

android {
    namespace = Config.namespace
    compileSdk = Config.maxSdk

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdk
        targetSdk = Config.maxSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = Config.runner
        signingConfig = signingConfigs.getByName("debug")
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

    kotlinOptions.jvmTarget = "1.8"

    viewBinding.enable = true
}

dependencies {

    implementation(Dependencies.Core.core)
    implementation(Dependencies.AppCompat.appcompat)
    implementation(Dependencies.Material.material)
    implementation(Dependencies.Constraint.contsraint)
    testImplementation(Dependencies.JUnit.junit)
    androidTestImplementation(Dependencies.JUnit.ext)
    androidTestImplementation(Dependencies.Espresso.core)

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Lifecycle.runtime)
    implementation(Dependencies.Lifecycle.viewModel)

    implementation(Dependencies.ViewBindingPropertyDelegate.noreflection)

    implementation(Dependencies.NavigationComponent.fragment)
    implementation(Dependencies.NavigationComponent.ui)

    implementation(Dependencies.Hilt.android)
    annotationProcessor(Dependencies.Hilt.compilier)
    kapt(Dependencies.Hilt.compilier)

    implementation(Dependencies.Room.core)
    implementation(Dependencies.Room.runtime)
    kapt(Dependencies.Room.compilier)

    implementation(Dependencies.LiveData.core)

    implementation(Dependencies.DataStorePreferences.core)

    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.OkHttp.core)
    implementation(Dependencies.OkHttp.logging)

    implementation(Dependencies.Activity.ktx)

    implementation(Dependencies.SplashScreen.core)

    implementation(Dependencies.RoundedProgressBar.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
}
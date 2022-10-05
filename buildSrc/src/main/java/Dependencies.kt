object Dependencies {
    object Core {
        private const val version = "1.9.0"
        const val core = "androidx.core:core-ktx:$version"
    }

    object AppCompat {
        private const val version = "1.5.1"
        const val appcompat = "androidx.appcompat:appcompat:$version"
    }

    object Material {
        private const val version = "1.6.1"
        const val material = "com.google.android.material:material:$version"
    }

    object Constraint {
        private const val version = "2.1.0"
        const val contsraint = "androidx.constraintlayout:constraintlayout:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        private const val versionExt = "1.1.3"
        const val junit = "junit:junit:$version"
        const val ext = "androidx.test.ext:junit:$versionExt"
    }

    object Espresso {
        private const val version = "3.4.0"
        const val core = "androidx.test.espresso:espresso-core:$version"
    }

    object Coroutines {
        private const val version = "1.6.4"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Lifecycle {
        private const val version = "2.5.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object ViewBindingPropertyDelegate {
        private const val version = "1.5.6"
        const val noreflection =
            "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$version"
    }

    object NavigationComponent {
        private const val version = "2.5.2"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Hilt {
        private const val version = "2.43.2"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compilier = "com.google.dagger:hilt-compiler:$version"
    }

    object Room {
        private const val version = "2.4.3"
        const val runtime = "androidx.room:room-runtime:$version"
        const val compilier = "androidx.room:room-compiler:$version"
        const val core = "androidx.room:room-ktx:$version"
    }

    object LiveData {
        private const val version = "2.5.1"
        const val core = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    }

    object DataStorePreferences {
        private const val version = "1.0.0"
        const val core = "androidx.datastore:datastore-preferences:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object OkHttp {
        private const val version = "4.9.3"
        const val core = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Activity {
        private const val version = "1.6.0"
        const val ktx = "androidx.activity:activity-ktx:$version"
    }

    object SplashScreen {
        private const val version = "1.0.0"
        const val core = "androidx.core:core-splashscreen:$version"
    }

    object RoundedProgressBar {
        private const val version = "2.1.2"
        const val core = "com.akexorcist:round-corner-progress-bar:$version"
    }
}
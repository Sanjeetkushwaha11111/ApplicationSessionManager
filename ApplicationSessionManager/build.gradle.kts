plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("maven-publish")

}

android {
    namespace = "com.sanjeet.sessionmanager"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation (libs.timberkt)
    testImplementation(libs.junit)


    implementation (libs.gson)
    implementation (libs.jsoup)
    implementation(libs.kotlinx.coroutines.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
publishing {
    publications {
        create<MavenPublication>("ReleaseAar") {
            groupId = "com.github.Sanjeetkushwaha11111"
            artifactId = "application-session-manager"
            version = "1.0.0"
            afterEvaluate {
                artifact(tasks.getByName("bundleReleaseAar"))
            }
        }
    }
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "dk.itu.moapd.infersnpe"
    compileSdk = 36

    defaultConfig {
        applicationId = "dk.itu.moapd.infersnpe"
        minSdk = 28
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("arm64-v8a") // Compile the APK only for ARM64 devices.
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

    packaging {
        jniLibs.useLegacyPackaging = true // Enable DSP support.
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

ktlint {
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)

    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()

    buildUponDefaultConfig = true
    allRules = false
    parallel = false

    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
}

tasks.named("check") {
    dependsOn("ktlintCheck")
}

tasks.register("fix") {
    group = "verification"
    description = "Auto-fix formatting issues (ktlint)."
    dependsOn("ktlintFormat", "ktlintKotlinScriptFormat")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(files("src/main/libs/snpe-release.aar"))
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
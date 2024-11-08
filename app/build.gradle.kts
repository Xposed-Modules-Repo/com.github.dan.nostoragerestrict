import com.android.build.api.artifact.ArtifactTransformationRequest
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.BuiltArtifact
import java.nio.file.Paths

plugins {
    id("com.android.application")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.dan.NoStorageRestrict"
        minSdk = 15
        targetSdk = 35
        versionCode = 5
        versionName = "0.5.0"
    }

    buildFeatures {
        prefab = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_21)
        targetCompatibility(JavaVersion.VERSION_21)
    }

    packagingOptions {
        resources {
            excludes += arrayOf("**")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro")
        }
    }

    lint {
        checkReleaseBuilds = false
    }

    dependenciesInfo {
        includeInApk = false
    }
    androidResources {
        additionalParameters += arrayOf("--allow-reserved-package-id", "--package-id", "0x23")
    }
    namespace = "io.github.duzhaokun123.takeapplog"
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}

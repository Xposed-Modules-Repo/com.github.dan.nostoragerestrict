import com.android.build.api.artifact.ArtifactTransformationRequest
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.BuiltArtifact
import java.nio.file.Paths
import java.util.Properties

plugins {
    id("com.android.application")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.dan.NoStorageRestrict"
        minSdk = 15
        targetSdk = 35
        versionCode = 6
        versionName = "0.6.0"

        vectorDrawables.generatedDensities?.clear()
    }

    buildFeatures {
        prefab = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_21)
        targetCompatibility(JavaVersion.VERSION_21)
    }

    packaging {
        resources {
            // Removed overly broad exclude to allow vcsInfo and other metadata
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-rules.pro")
            vcsInfo.include = true
            signingConfig = signingConfigs.findByName("release")
            isCrunchPngs = false
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

    signingConfigs {
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (keystorePropertiesFile.exists()) {
            val keystoreProperties = Properties()
            keystorePropertiesFile.inputStream().use { keystoreProperties.load(it) }
            create("release") {
                storeFile = file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        } else {
            create("release")
        }
    }
    namespace = "com.github.dan.NoStorageRestrict"
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}

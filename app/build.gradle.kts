import com.android.build.api.artifact.ArtifactTransformationRequest
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.BuiltArtifact
import java.nio.file.Paths

plugins {
    id("com.android.application")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.github.dan.NoStorageRestrict"
        minSdk = 15
        targetSdk = 32  // Target Android Sv2
        versionCode = 2
        versionName = "0.2.0"
    }

    buildFeatures {
        prefab = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    packagingOptions {
        resources {
            excludes += arrayOf("**")
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
        all {
            isMinifyEnabled = false
            isShrinkResources = false
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

abstract class CopyApksTask : DefaultTask() {
    @get:Internal
    abstract val transformer: Property<(input: BuiltArtifact) -> File>

    @get:InputDirectory
    abstract val apkFolder: DirectoryProperty

    @get:OutputDirectory
    abstract val outFolder: DirectoryProperty

    @get:Internal
    abstract val transformationRequest: Property<ArtifactTransformationRequest<CopyApksTask>>

    @TaskAction
    fun taskAction() = transformationRequest.get().submit(this) { builtArtifact ->
        File(builtArtifact.outputFile).copyTo(transformer.get()(builtArtifact), true)
    }
}

androidComponents.onVariants { variant ->
    if (variant.name != "release") return@onVariants
    val updateArtifact = project.tasks.register<CopyApksTask>("copy${variant.name.capitalize()}Apk")
    val transformationRequest = variant.artifacts.use(updateArtifact)
        .wiredWithDirectories(CopyApksTask::apkFolder, CopyApksTask::outFolder)
        .toTransformMany(SingleArtifact.APK)
    updateArtifact.configure {
        this.transformationRequest.set(transformationRequest)
        transformer.set { builtArtifact ->
            File(projectDir, "${variant.name}/TakeAppLog_${builtArtifact.versionName}.apk")
        }
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}

val optimizeReleaseRes = task("optimizeReleaseRes").doLast {
    val aapt2 = Paths.get(
        project.android.sdkDirectory.path,
        "build-tools", project.android.buildToolsVersion, "aapt2"
    )
    val zip = Paths.get(
        project.buildDir.path, "intermediates",
        "optimized_processed_res", "release", "resources-release-optimize.ap_"
    )
    val optimized = File("${zip}.opt")
    val cmd = exec {
        commandLine(aapt2, "optimize", "--collapse-resource-names", "-o", optimized, zip)
        isIgnoreExitValue = true
    }
    if (cmd.exitValue == 0) {
        delete(zip)
        optimized.renameTo(zip.toFile())
    }
}
tasks.whenTaskAdded {
    when (name) {
        "optimizeReleaseResources" -> {
            finalizedBy(optimizeReleaseRes)
        }
    }
}

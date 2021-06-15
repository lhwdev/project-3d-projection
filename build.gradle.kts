import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("multiplatform") version "1.5.10"
    id("org.jetbrains.compose") version "0.5.0-build224"
}

group = "com.lhwdev.com.lhwdev.math.projection-3d"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "13"
        }
    }
    
    js(IR) {
        browser {
            webpackTask {
                sourceMaps = true
                devtool = "source-map"
            }
            
            compilations.all {
                kotlinOptions.sourceMap = true
                kotlinOptions.sourceMapEmbedSources = "always"
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-io:0.1.16")
            }
        }
        
        named("jvmMain") {
            dependencies {
                implementation(compose.ui)
                implementation(compose.desktop.currentOs)
            }
        }
    
        named("jsMain") {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "projection-3d"
            packageVersion = "1.0.0"
        }
    }
}

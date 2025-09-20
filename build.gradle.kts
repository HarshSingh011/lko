// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.squareup:javapoet:1.13.0")
    }
}

// Force a specific JavaPoet version to avoid "NoSuchMethodError" caused
// by multiple processors bringing different JavaPoet versions onto the
// build classpath. This ensures annotation processors (Hilt/KSP/etc.) use
// a compatible JavaPoet at build time.
subprojects {
    plugins.withType(org.gradle.api.plugins.JavaBasePlugin::class.java) {
        configurations.configureEach {
            resolutionStrategy.force("com.squareup:javapoet:1.13.0")
        }
    }
}
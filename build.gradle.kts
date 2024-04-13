// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.chaquo.python") version "15.0.1" apply false
}
val ndkVersion by extra("C:\\Users\\Tanya\\AppData\\Local\\Programs\\Python\\Python38")

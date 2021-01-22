buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.4.21"
    var hiltVersion: String by extra
    hiltVersion = "2.28.3-alpha"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(kotlin("serialization", kotlinVersion))
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
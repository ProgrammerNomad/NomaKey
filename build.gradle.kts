// Top-level build file for NomaKey
plugins {
    id("com.android.application") version "8.13.2" apply false
    id("com.android.library") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

// Optional: keep clean task for compatibility
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}


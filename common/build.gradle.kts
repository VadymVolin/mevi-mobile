plugins {
    id(libs.plugins.java.library.plugin.get().pluginId)
    alias(libs.plugins.android.kotlin.jvm.plugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
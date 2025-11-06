plugins {
    id("org.jetbrains.kotlin.jvm")
    id("youth.kotlin.library")
}

java {

}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.hilt.core)
    implementation(libs.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)
}

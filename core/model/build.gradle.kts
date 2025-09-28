plugins {
    id("youth.kotlin.library")
    id("record.hilt")
    kotlin("plugin.serialization")
}

java {

}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}

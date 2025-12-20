plugins {
    id("youth.android.feature")
}

android {
    namespace = "record.daily.login"
}

dependencies {
    implementation(libs.kakao.v2.user)
}

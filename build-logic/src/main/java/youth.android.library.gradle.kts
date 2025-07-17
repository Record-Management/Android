import com.youth.app.configureCoroutineAndroid
import com.youth.app.configureHilt
import com.youth.app.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHilt()

package record.daily.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import record.daily.android.timber.ClassNameCustomTimber
import timber.log.Timber

@HiltAndroidApp
class RecordApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        if(BuildConfig.DEBUG) {
            Timber.plant(ClassNameCustomTimber())
        }
    }
}

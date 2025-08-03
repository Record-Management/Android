package record.daily.login.social

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

internal fun kakaoLogin(context: Context, onSuccess: (Long) -> Unit) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error, "카카오계정으로 로그인 실패")
        } else if (token != null) {
            Timber.i("카카오계정으로 로그인 성공 " + token.accessToken)
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Timber.e("카카오 계정 정보 가져오기 실패")
                } else if (tokenInfo != null) {
                    onSuccess(tokenInfo.id ?: -1)
                }
            }
        }
    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Timber.e(error, "카카오톡으로 로그인 실패")
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Timber.i("카카오톡으로 로그인 성공 " + token.accessToken + " " + token.accessTokenExpiresAt)
                UserApiClient.instance.me { user, instanceError ->
                    if (instanceError != null) {
                        Timber.e("$instanceError 사용자 정보 요청 실패")
                    } else if (user != null) {
                        onSuccess(user.id ?: -1)
                    }
                }
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}

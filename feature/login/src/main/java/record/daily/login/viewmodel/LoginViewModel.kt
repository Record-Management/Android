package record.daily.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import record.daily.login.state.login.LoginUiEffect
import see.day.analytics.AnalyticsEvent
import see.day.analytics.AnalyticsLogger
import see.day.domain.repository.LoginRepository
import see.day.domain.repository.UserRepository
import see.day.domain.usecase.fcm.GetFcmTokenUseCase
import see.day.model.login.SocialLogin
import see.day.model.login.SocialType
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.ONBOARDING

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository,
    private val analyticsLogger: AnalyticsLogger,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
) : ViewModel() {

    private val _uiEffect: MutableSharedFlow<LoginUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<LoginUiEffect> = _uiEffect.asSharedFlow()

    fun isAppFirstLaunch() {
        viewModelScope.launch {
            val isAppFirstLaunched = loginRepository.isAppFirstLaunch()
            if (isAppFirstLaunched) {
                _uiEffect.emit(LoginUiEffect.GoPermission)
            }
        }
    }

    fun login(socialType: SocialType, accessToken: String) {
        viewModelScope.launch {
            loginRepository.login(SocialLogin(socialType, accessToken)).onSuccess {
                if (it == ONBOARDING) {
                    analyticsLogger.log(AnalyticsEvent.SignUp)
                    _uiEffect.emit(LoginUiEffect.GoOnboarding)
                } else if (it == HOME) {
                    getFcmTokenUseCase().onSuccess { fcmToken ->
                        userRepository.updateFcmToken(fcmToken)
                    }
                    analyticsLogger.log(AnalyticsEvent.Login)
                    _uiEffect.emit(LoginUiEffect.GoHome)
                }
            }.onFailure {
            }
        }
    }
}

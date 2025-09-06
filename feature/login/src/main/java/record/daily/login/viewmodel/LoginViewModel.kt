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
import see.day.domain.usecase.login.PostLoginUseCase
import see.day.model.login.SocialLogin
import see.day.model.login.SocialType
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.ONBOARDING

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase
) : ViewModel() {

    private val _uiEffect: MutableSharedFlow<LoginUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<LoginUiEffect> = _uiEffect.asSharedFlow()

    fun login(socialType: SocialType, accessToken: String) {
        viewModelScope.launch {
            postLoginUseCase(SocialLogin(socialType, accessToken)).onSuccess {
                if (it == ONBOARDING) {
                    _uiEffect.emit(LoginUiEffect.GoOnboarding)
                } else if (it == HOME) {
                    _uiEffect.emit(LoginUiEffect.GoHome)
                }
            }.onFailure {
            }
        }
    }
}

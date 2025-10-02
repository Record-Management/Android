package see.day.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import see.day.domain.usecase.login.GetLoginStateUseCase
import see.day.model.navigation.AppStartState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {

    private val _startDestination : MutableStateFlow<AppStartState?> = MutableStateFlow(null)
    val startDestination : StateFlow<AppStartState?> = _startDestination.asStateFlow()

    private val _navigationEvent : MutableStateFlow<AppStartState?> = MutableStateFlow(null)
    val navigationEvent : StateFlow<AppStartState?> = _navigationEvent.asStateFlow()

    init {
        viewModelScope.launch {
            getLoginStateUseCase().collect { newLoginState ->
                if(startDestination.value == null) {
                    _startDestination.emit(newLoginState)
                } else {
                    when(newLoginState) {
                        AppStartState.LOGIN -> {
                            _navigationEvent.emit(newLoginState)
                        }
                        AppStartState.ONBOARDING -> {
                            _navigationEvent.emit(newLoginState)
                        }
                        AppStartState.HOME -> {
                            if(startDestination.value != AppStartState.ONBOARDING || navigationEvent.value != AppStartState.ONBOARDING) {
                                _navigationEvent.emit(newLoginState)
                            }
                        }
                    }
                }
            }
        }
    }
}

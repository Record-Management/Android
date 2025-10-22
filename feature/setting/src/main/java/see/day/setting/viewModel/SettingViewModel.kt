package see.day.setting.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.domain.usecase.login.LogoutUseCase
import see.day.domain.usecase.user.DeleteUserUseCase
import see.day.domain.usecase.user.GetUserUseCase
import see.day.domain.usecase.user.UpdateUserProfileUseCase
import see.day.model.user.UserProfileChangedInput
import see.day.setting.state.SettingUiEffect
import see.day.setting.state.SettingUiEvent
import see.day.setting.state.SettingUiState
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingUiState> = MutableStateFlow(SettingUiState.init)
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<SettingUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<SettingUiEffect> = _uiEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getUserUseCase()
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(
                            id = user.id,
                            nickname = user.nickname,
                            birthDate = user.birthDate,
                            socialType = user.socialType
                        )
                    }
                }
        }
    }

    fun onEvent(uiEvent: SettingUiEvent) {
        when (uiEvent) {
            is SettingUiEvent.OnPopBack -> {
                onPopBack()
            }

            is SettingUiEvent.OnChangedNickname -> {
                onChangedNickname(uiEvent.nickname)
            }

            is SettingUiEvent.OnChangedBirthDate -> {
                onChangedBirthday(uiEvent.birthDate)
            }
        }
    }

    private fun onPopBack() {
        viewModelScope.launch {
            _uiEffect.emit(SettingUiEffect.OnPopBack)
        }
    }

    private fun onChangedNickname(nickname: String) {
        viewModelScope.launch {
            updateUserProfileUseCase(UserProfileChangedInput.ofNickname(nickname))
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(
                            nickname = user.nickname
                        )
                    }
                }
        }
    }

    private fun onChangedBirthday(birthDate: String) {
        viewModelScope.launch {
            updateUserProfileUseCase(UserProfileChangedInput.ofBirthDate(birthDate))
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(
                            birthDate = user.birthDate
                        )
                    }
                }
        }
    }
}

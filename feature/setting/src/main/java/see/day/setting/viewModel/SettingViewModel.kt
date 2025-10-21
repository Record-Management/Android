package see.day.setting.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import see.day.domain.usecase.login.LogoutUseCase
import see.day.domain.usecase.user.DeleteUserUseCase
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel(){

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase()
        }
    }
}

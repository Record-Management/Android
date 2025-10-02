package see.day.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import see.day.domain.usecase.login.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): ViewModel(){

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

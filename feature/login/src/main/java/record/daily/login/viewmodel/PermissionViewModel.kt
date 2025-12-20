package record.daily.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import record.daily.login.state.permission.PermissionUiEffect
import record.daily.login.state.permission.PermissionUiEvent
import see.day.domain.usecase.login.SetAppFirstLaunchUseCase
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val setAppFirstLaunchUseCase: SetAppFirstLaunchUseCase
) : ViewModel() {

    private val _uiEffect: MutableSharedFlow<PermissionUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<PermissionUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(uiEvent: PermissionUiEvent) {
        when (uiEvent) {
            PermissionUiEvent.OnClickPermissionConfirm -> {
                setAppFirstLaunch()
            }
        }
    }

    private fun setAppFirstLaunch() {
        viewModelScope.launch {
            setAppFirstLaunchUseCase()
            _uiEffect.emit(PermissionUiEffect.OnGoLogin)
        }
    }
}

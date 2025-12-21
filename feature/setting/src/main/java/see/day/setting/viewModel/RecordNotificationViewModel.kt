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
import see.day.domain.usecase.notifiaction.GetNotificationSettingUseCase
import see.day.domain.usecase.notifiaction.UpdateNotificationSettingUseCase
import see.day.model.notification.NotificationSettingsEdit
import see.day.setting.state.record.RecordNotificationUiEffect
import see.day.setting.state.record.RecordNotificationUiEvent
import see.day.setting.state.record.RecordNotificationUiState
import javax.inject.Inject

@HiltViewModel
class RecordNotificationViewModel @Inject constructor(
    private val getNotificationSettingUseCase: GetNotificationSettingUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<RecordNotificationUiState> = MutableStateFlow(RecordNotificationUiState.init)
    val uiState: StateFlow<RecordNotificationUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<RecordNotificationUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<RecordNotificationUiEffect> = _uiEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getNotificationSettingUseCase()
                .onSuccess { notificationSetting ->
                    _uiState.update {
                        it.copy(
                            dailyRecordNotificationEnabled = notificationSetting.dailyRecordEnabled,
                            exerciseRecordNotificationEnabled = notificationSetting.exerciseRecordEnabled,
                            habitRecordNotificationEnabled = notificationSetting.habitRecordEnabled
                        )
                    }
                }
        }
    }

    fun onAction(uiEvent: RecordNotificationUiEvent) {
        when (uiEvent) {
            RecordNotificationUiEvent.OnClickBack -> {
                onGoBack()
            }

            is RecordNotificationUiEvent.OnChangedRecordNotification -> {
                onChangedRecordNotification(
                    dailyRecordEnabled = uiEvent.dailyRecordNotificationEnabled,
                    exerciseRecordEnabled = uiEvent.exerciseRecordNotificationEnabled,
                    habitRecordEnabled = uiEvent.habitRecordNotificationEnabled
                )
            }
        }
    }

    private fun onGoBack() {
        viewModelScope.launch {
            _uiEffect.emit(RecordNotificationUiEffect.NavigateToBackStack)
        }
    }

    private fun onChangedRecordNotification(
        dailyRecordEnabled: Boolean?,
        exerciseRecordEnabled: Boolean?,
        habitRecordEnabled: Boolean?
    ) {
        viewModelScope.launch {
            updateNotificationSettingUseCase(
                NotificationSettingsEdit(
                    dailyRecordNotificationEnabled = dailyRecordEnabled,
                    exerciseNotificationEnabled = exerciseRecordEnabled,
                    habitNotificationEnabled = habitRecordEnabled
                )
            ).onSuccess { notificationSetting ->
                _uiState.update {
                    it.copy(
                        dailyRecordNotificationEnabled = notificationSetting.dailyRecordEnabled,
                        exerciseRecordNotificationEnabled = notificationSetting.exerciseRecordEnabled,
                        habitRecordNotificationEnabled = notificationSetting.habitRecordEnabled
                    )
                }
            }
        }
    }
}

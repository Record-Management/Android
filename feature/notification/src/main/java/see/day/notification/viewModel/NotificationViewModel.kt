package see.day.notification.viewModel

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
import see.day.domain.usecase.calendar.GetDailyRecordsCountUseCase
import see.day.domain.usecase.notifiaction.GetNotificationHistoryUseCase
import see.day.domain.usecase.notifiaction.UpdateNotificationHistoryAllReadUseCase
import see.day.model.record.RecordType
import see.day.notification.state.NotificationHistoryUiModel
import see.day.notification.state.NotificationUiEffect
import see.day.notification.state.NotificationUiEvent
import see.day.notification.state.NotificationUiState
import see.day.notification.util.TimeFormatUtil
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationHistoryUseCase: GetNotificationHistoryUseCase,
    private val updateNotificationHistoryAllReadUseCase: UpdateNotificationHistoryAllReadUseCase,
    private val getDailyRecordsCountUseCase: GetDailyRecordsCountUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(NotificationUiState.init)
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<NotificationUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<NotificationUiEffect> = _uiEffect.asSharedFlow()

    private val _toastMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            getNotificationHistoryUseCase()
                .onSuccess { history ->
                    val now = LocalDate.now(ZoneId.of("Asia/Seoul"))
                    val todayDateString = now.toFormattedString()

                    // 첫 방문이거나 recentCheckedAt이 없으면 전체 읽음 처리
                    val recentCheckedAt = history.recentCheckedAt ?: run {
                        updateNotificationHistoryAllReadUseCase()
                        todayDateString
                    }

                    // UI 모델 변환
                    val uiModel = history.notifications.map { notification ->
                        NotificationHistoryUiModel(
                            recordType = notification.recordType,
                            relativeTime = TimeFormatUtil.getRelativeTimeString(notification.sentAt),
                            isChecked = notification.sentAt < recentCheckedAt
                        )
                    }

                    // 읽지 않은 알림이 있으면 읽음 처리
                    if (uiModel.any { !it.isChecked }) {
                        updateNotificationHistoryAllReadUseCase()
                    }

                    val todayRecordCount = getDailyRecordsCountUseCase(todayDateString)

                    _uiState.update {
                        it.copy(
                            recentCheckedAt = recentCheckedAt,
                            notificationHistories = uiModel,
                            todayRecordCount = todayRecordCount
                        )
                    }
                }
        }
    }

    fun onEvent(uiEvent: NotificationUiEvent) {
        when (uiEvent) {
            NotificationUiEvent.OnClickBack -> {
                onClickBack()
            }

            is NotificationUiEvent.OnClickItem -> {
                onClickItem(uiEvent.recordType, uiEvent.relativeTime)
            }
        }
    }

    private fun onClickBack() {
        viewModelScope.launch {
            _uiEffect.emit(NotificationUiEffect.OnPopBack)
        }
    }

    private fun onClickItem(recordType: RecordType, relativeTime: String) {
        viewModelScope.launch {
            if (relativeTime.contains("시간 전")) {
                if (uiState.value.todayRecordCount >= 2) {
                    _toastMessage.emit("이미 기록을 작성했어요.")
                    _uiEffect.emit(NotificationUiEffect.OnPopBack)
                } else {
                    _uiEffect.emit(NotificationUiEffect.GoWriteRecord(recordType))
                }
            } else if (relativeTime.contains("일 전")) {
                if (uiState.value.todayRecordCount >= 2) {
                    _toastMessage.emit("지나간 기록은 기록할 수 없어요. 내일 또 만나요!")
                    _uiEffect.emit(NotificationUiEffect.OnPopBack)
                } else {
                    _toastMessage.emit(
                        "지나간 기록은 기록할 수 없어요. \n" +
                            "오늘의 기록을 작성해 보는건 어떨까요?"
                    )
                    _uiEffect.emit(NotificationUiEffect.GoWriteRecord(recordType))
                }
            }
        }

    }

    private fun LocalDate.toFormattedString(): String {
        return String.format("%04d-%02d-%02d", year, monthValue, dayOfMonth)
    }
}

package see.day.daily.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.daily.state.DailyDetailUiEffect
import see.day.daily.state.DailyDetailUiEvent
import see.day.daily.state.DailyDetailUiState
import see.day.daily.util.DailyRecordPostType
import see.day.domain.usecase.photo.InsertPhotosUseCase
import see.day.domain.usecase.record.daily.InsertDailyRecordUseCase
import see.day.model.record.daily.CreateDailyRecord
import see.day.model.record.daily.DailyEmotion
import javax.inject.Inject

@HiltViewModel
class DailyDetailViewModel @Inject constructor(
    private val insertPhotosUseCase: InsertPhotosUseCase,
    private val insertDailyRecordUseCase: InsertDailyRecordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DailyDetailUiState> = MutableStateFlow(DailyDetailUiState.init)
    val uiState: StateFlow<DailyDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<DailyDetailUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<DailyDetailUiEffect> = _uiEffect.asSharedFlow()

    fun fetchData(type: DailyRecordPostType) {
        when (type) {
            is DailyRecordPostType.WriteDailyRecordPost -> {
                _uiState.update {
                    it.copy(
                        emotion = type.emotion,
                        editMode = DailyDetailUiState.EditMode.Create,
                    )
                }
            }

            is DailyRecordPostType.EditDailyRecordPost -> {

            }
        }
    }

    fun onEvent(uiEvent: DailyDetailUiEvent) {
        when (uiEvent) {
            DailyDetailUiEvent.OnPopHome -> {
                onPopHome()
            }

            is DailyDetailUiEvent.OnAddPhotos -> {
                onAddPhotos(photos = uiEvent.photos)
            }

            is DailyDetailUiEvent.OnChangeDailyEmotion -> {
                onChangedEmotion(uiEvent.emotion)
            }

            is DailyDetailUiEvent.OnChangedText -> {
                onChangedText(uiEvent.text)
            }

            is DailyDetailUiEvent.OnRemovePhoto -> {
                onRemovePhoto(uiEvent.photo)
            }

            is DailyDetailUiEvent.OnSaveRecord -> {
                onSaveRecord()
            }
        }
    }

    private fun onPopHome() {
        viewModelScope.launch {
            _uiEffect.emit(DailyDetailUiEffect.OnPopHome)
        }
    }

    private fun onAddPhotos(photos: List<String>) {
        _uiState.update {
            val addPhotos = (it.photos + photos).distinct()
            it.copy(
                photos = if (addPhotos.size <= 3) {
                    addPhotos
                } else {
                    addPhotos.subList(0, 3)
                }
            )
        }
    }

    private fun onChangedEmotion(emotion: DailyEmotion) {
        _uiState.update {
            it.copy(
                emotion = emotion
            )
        }
    }

    private fun onChangedText(text: String) {
        _uiState.update {
            it.copy(
                text = text
            )
        }
    }

    private fun onRemovePhoto(photo: String) {
        _uiState.update {
            it.copy(
                photos = it.photos.filter {
                    photo != it
                }
            )
        }
    }

    private fun onSaveRecord() {
        viewModelScope.launch {
            when (uiState.value.editMode) {
                is DailyDetailUiState.EditMode.Create -> saveDailyRecordForCreateMode()
                is DailyDetailUiState.EditMode.Edit -> saveDailyRecordForEditMode()
            }
        }
    }

    private suspend fun saveDailyRecordForCreateMode() {
        val photos = uiState.value.photos
        if (photos.isNotEmpty()) {
            insertPhotosUseCase(photos).fold(
                onSuccess = { photoUrls -> saveDailyRecord(photoUrls) },
                onFailure = { handleSaveError(it) }
            )
        } else {
            saveDailyRecord(emptyList())
        }
    }

    private suspend fun saveDailyRecord(photoUrls: List<String>) {
        insertDailyRecordUseCase(
            CreateDailyRecord(
                uiState.value.text,
                uiState.value.emotion.name,
                uiState.value.dateTime,
                photoUrls
            )
        ).fold(
            onSuccess = { _uiEffect.emit(DailyDetailUiEffect.OnPopHome) },
            onFailure = { handleSaveError(it) }
        )
    }

    private fun handleSaveError(error: Throwable) {
        // 실패 처리 로직 추가 (예: Toast, 로그, UI 에러 상태 반영)
    }

    private suspend fun saveDailyRecordForEditMode() {
        // Edit 모드 저장 로직 구현
    }
}

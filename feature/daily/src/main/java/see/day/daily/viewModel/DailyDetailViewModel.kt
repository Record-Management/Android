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
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import see.day.daily.state.DailyDetailUiEffect
import see.day.daily.state.DailyDetailUiEvent
import see.day.daily.state.DailyDetailUiState
import see.day.daily.util.DailyRecordPostType
import see.day.designsystem.util.DailyEmotion
import see.day.domain.usecase.photo.InsertPhotosUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DailyDetailViewModel @Inject constructor(
    private val insertPhotosUseCase: InsertPhotosUseCase
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
            if (uiState.value.photos.isNotEmpty()) {
                val photos = uiState.value.photos

                insertPhotosUseCase(photos)
                    .onSuccess {

                    }.onFailure {

                    }
            }
        }
        // 사진 저장
        // 저장 후 데이터 저장
    }

}

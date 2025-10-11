package see.day.daily.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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
import see.day.domain.usecase.record.daily.DeleteDailyRecordUseCase
import see.day.domain.usecase.record.daily.GetDailyRecordUseCase
import see.day.domain.usecase.record.daily.InsertDailyRecordUseCase
import see.day.domain.usecase.record.daily.UpdateDailyRecordUseCase
import see.day.model.calendar.DailyRecordDetail
import see.day.model.record.daily.DailyRecordInput
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.daily.DailyRecordEdit
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

@HiltViewModel
class DailyDetailViewModel @Inject constructor(
    private val insertPhotosUseCase: InsertPhotosUseCase,
    private val insertDailyRecordUseCase: InsertDailyRecordUseCase,
    private val getDetailRecordUseCase: GetDailyRecordUseCase,
    private val updateDetailRecordUseCase : UpdateDailyRecordUseCase,
    private val deleteDailyRecordUseCase: DeleteDailyRecordUseCase
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
                        editMode = DailyDetailUiState.EditMode.Create
                    )
                }
            }

            is DailyRecordPostType.EditDailyRecordPost -> {
                viewModelScope.launch {
                    getDetailRecordUseCase(type.id).onSuccess {  record ->
                        if(record is DailyRecordDetail) {
                            _uiState.update {
                                it.copy(
                                    emotion = record.emotion,
                                    text = record.content,
                                    dateTime = KoreanDateTimeFormatter(DateTime.of(record.recordDate, record.recordTime)),
                                    photos = record.imageUrls,
                                    editMode = DailyDetailUiState.EditMode.Edit(
                                        recordId = record.id,
                                        originalRecord = DailyRecordInput(
                                            content = record.content,
                                            emotion = record.emotion,
                                            recordDate = KoreanDateTimeFormatter(DateTime.of(record.recordDate, record.recordTime)),
                                            imageUrls = record.imageUrls,
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
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
            is DailyDetailUiEvent.DeleteRecord -> {
                onDeleteRecord(uiEvent.recordId)
            }
        }
    }

    private fun onPopHome() {
        viewModelScope.launch {
            _uiEffect.emit(DailyDetailUiEffect.OnPopHome(false))
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
            when (val mode = uiState.value.editMode) {
                is DailyDetailUiState.EditMode.Create -> saveDailyRecordForCreateMode()
                is DailyDetailUiState.EditMode.Edit -> updateRecord(mode.recordId)
            }
        }
    }

    private suspend fun saveDailyRecordForCreateMode() {
        val photos = uiState.value.photos
        if (photos.isNotEmpty()) {
            insertPhotosUseCase(photos).fold(
                onSuccess = { photoUrls -> saveDailyRecord(photoUrls) },
                onFailure = {  }
            )
        } else {
            saveDailyRecord(emptyList())
        }
    }

    private suspend fun saveDailyRecord(photoUrls: List<String>) {
        insertDailyRecordUseCase(
            DailyRecordInput(
                uiState.value.text,
                uiState.value.emotion,
                uiState.value.dateTime,
                photoUrls
            )
        ).fold(
            onSuccess = { _uiEffect.emit(DailyDetailUiEffect.OnPopHome(true)) },
            onFailure = {  }
        )
    }

    private suspend fun updateRecord(recordId: String) {
        val urls = processPhotoUrls(uiState.value.photos)

        updateDetailRecordUseCase(
            DailyRecordEdit(
                recordId,
                uiState.value.text,
                uiState.value.emotion,
                urls
            )
        ).onSuccess {
            _uiEffect.emit(DailyDetailUiEffect.OnPopHome(true))
        }
    }

    private suspend fun processPhotoUrls(photoUrls: List<String>): List<String> {
        return if (photoUrls.all { it.contains("http") }) {
            photoUrls
        } else {
            photoUrls.map { url ->
                if (url.contains("content")) {
                    insertPhotosUseCase(listOf(url)).getOrElse { listOf("") }[0]
                } else {
                    url
                }
            }.filter { it.isNotEmpty() }
        }
    }

    private fun onDeleteRecord(recordId: String) {
        viewModelScope.launch {
            deleteDailyRecordUseCase(recordId)
                .onSuccess {
                    _uiEffect.emit(DailyDetailUiEffect.OnPopHome(isUpdated = true))
                }
        }
    }
}

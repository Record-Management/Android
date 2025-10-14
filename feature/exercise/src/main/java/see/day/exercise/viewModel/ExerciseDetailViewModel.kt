package see.day.exercise.viewModel

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
import see.day.domain.usecase.photo.InsertPhotosUseCase
import see.day.domain.usecase.record.daily.GetRecordDetailUseCase
import see.day.domain.usecase.record.exercise.InsertExerciseRecordUseCase
import see.day.exercise.state.ExerciseDailyUiEffect
import see.day.exercise.state.ExerciseDetailUiEvent
import see.day.exercise.state.ExerciseDetailUiState
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.calendar.ExerciseRecordDetail
import see.day.model.record.exercise.ExerciseRecordInput
import see.day.model.record.exercise.ExerciseType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    val insertPhotosUseCase: InsertPhotosUseCase,
    val insertExerciseRecordUseCase: InsertExerciseRecordUseCase,
    val getRecordDetailUseCase: GetRecordDetailUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ExerciseDetailUiState> = MutableStateFlow(ExerciseDetailUiState.init)
    val uiState: StateFlow<ExerciseDetailUiState> = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<ExerciseDailyUiEffect> = MutableSharedFlow()
    val uiEffect: SharedFlow<ExerciseDailyUiEffect> = _uiEffect.asSharedFlow()

    fun fetchData(type: ExerciseRecordPostType) {
        when (type) {
            is ExerciseRecordPostType.Write -> {
                _uiState.update {
                    it.copy(
                        exerciseType = type.exerciseType,
                    )
                }
            }

            is ExerciseRecordPostType.Edit -> {
                viewModelScope.launch {
                    getRecordDetailUseCase(type.id).onSuccess { record ->
                        if(record is ExerciseRecordDetail) {
                            _uiState.update {
                                it.copy(
                                    exerciseType = record.exerciseType,
                                    dailyNote = record.dailyNote,
                                    recordDate = KoreanDateTimeFormatter(DateTime.of(recordDate = record.recordDate, recordTime = record.recordTime)),
                                    caloriesBurned = record.caloriesBurned,
                                    exerciseTimeMinutes = record.exerciseTimeMinutes,
                                    stepCount = record.stepCount,
                                    weight = record.weight,
                                    imageUrls = record.imageUrls,
                                    editMode = ExerciseDetailUiState.EditMode.Edit(
                                        originalRecord = ExerciseRecordInput(
                                            exerciseType = record.exerciseType,
                                            dailyNote = record.dailyNote,
                                            recordDate = KoreanDateTimeFormatter(DateTime.of(recordDate = record.recordDate, recordTime = record.recordTime)),
                                            caloriesBurned = record.caloriesBurned,
                                            exerciseTimeMinutes = record.exerciseTimeMinutes,
                                            stepCount = record.stepCount,
                                            weight = record.weight,
                                            imageUrls = record.imageUrls
                                        ),
                                        recordId = type.id
                                    )
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    fun onEvent(uiEvent: ExerciseDetailUiEvent) {
        when (uiEvent) {
            is ExerciseDetailUiEvent.OnExerciseTypeChanged -> onChangedExerciseType(uiEvent.exerciseType)
            is ExerciseDetailUiEvent.OnDailyNoteChanged -> onDailyNoteChanged(dailyNote = uiEvent.dailyNote)
            is ExerciseDetailUiEvent.OnCaloriesChanged -> onCaloriesChanged(uiEvent.caloriesBurn)
            is ExerciseDetailUiEvent.OnExerciseTimeChanged -> onExerciseTimeChanged(uiEvent.minutes)
            is ExerciseDetailUiEvent.OnStepCountChanged -> onStepCountChanged(uiEvent.stepCount)
            is ExerciseDetailUiEvent.OnWeightChanged -> onWeightChanged(uiEvent.weight)
            is ExerciseDetailUiEvent.OnAddPhotos -> onAddPhotos(uiEvent.urls)
            is ExerciseDetailUiEvent.OnRemovePhoto -> onRemovePhoto(uiEvent.url)
            is ExerciseDetailUiEvent.OnSaveRecord -> onSaveRecord()
        }
    }

    private fun onChangedExerciseType(exerciseType: ExerciseType) {
        _uiState.update {
            it.copy(
                exerciseType = exerciseType
            )
        }
    }

    private fun onDailyNoteChanged(dailyNote: String) {
        _uiState.update {
            it.copy(
                dailyNote = dailyNote
            )
        }
    }

    private fun onCaloriesChanged(calories: String) {
        _uiState.update {
            it.copy(
                caloriesBurned = calories
            )
        }
    }

    private fun onExerciseTimeChanged(exerciseTime: String) {
        _uiState.update {
            it.copy(
                exerciseTimeMinutes = exerciseTime
            )
        }
    }

    private fun onStepCountChanged(stepCount: String) {
        _uiState.update {
            it.copy(
                stepCount = stepCount
            )
        }
    }

    private fun onWeightChanged(weight: String) {
        _uiState.update {
            it.copy(
                weight = weight
            )
        }
    }

    private fun onAddPhotos(photos: List<String>) {
        _uiState.update {
            val addPhotos = (it.imageUrls + photos).distinct()
            it.copy(
                imageUrls = if (addPhotos.size <= 3) {
                    addPhotos
                } else {
                    addPhotos.subList(0, 3)
                }
            )
        }
    }

    private fun onRemovePhoto(photo: String) {
        _uiState.update {
            it.copy(
                imageUrls = it.imageUrls.filter {
                    photo != it
                }
            )
        }
    }

    private fun onSaveRecord() {
        viewModelScope.launch {
            when (val mode = uiState.value.editMode) {
                is ExerciseDetailUiState.EditMode.Create -> {
                    saveExerciseRecordForCreateMode()
                }

                is ExerciseDetailUiState.EditMode.Edit -> {}
            }
        }
    }

    private suspend fun saveExerciseRecordForCreateMode() {
        val imageUrls = uiState.value.imageUrls
        if (imageUrls.isNotEmpty()) {
            insertPhotosUseCase(imageUrls).fold(
                onSuccess = { urls -> saveExerciseRecord(urls) },
                onFailure = {}
            )
        }
    }

    private suspend fun saveExerciseRecord(imageUrls: List<String>) {
        insertExerciseRecordUseCase(
            ExerciseRecordInput(
                uiState.value.exerciseType,
                dailyNote = uiState.value.dailyNote,
                recordDate = uiState.value.recordDate,
                caloriesBurned = uiState.value.caloriesBurned,
                exerciseTimeMinutes = uiState.value.exerciseTimeMinutes,
                stepCount = uiState.value.stepCount,
                weight = uiState.value.weight,
                imageUrls = imageUrls,
            )
        ).fold(
            onSuccess = { _uiEffect.emit(ExerciseDailyUiEffect.OnPopHome(true)) },
            onFailure = {}
        )
    }
}

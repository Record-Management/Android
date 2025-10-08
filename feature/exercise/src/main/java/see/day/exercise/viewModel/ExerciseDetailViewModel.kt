package see.day.exercise.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import see.day.exercise.state.ExerciseDetailUiEvent
import see.day.exercise.state.ExerciseDetailUiState
import see.day.exercise.util.ExerciseRecordPostType
import see.day.model.record.exercise.ExerciseType
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState: MutableStateFlow<ExerciseDetailUiState> = MutableStateFlow(ExerciseDetailUiState.init)
    val uiState: StateFlow<ExerciseDetailUiState> = _uiState.asStateFlow()

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

    }
}

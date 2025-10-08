package see.day.exercise.state

import see.day.model.record.exercise.ExerciseType

sealed interface ExerciseDetailUiEvent {
    data class OnExerciseTypeChanged(val exerciseType: ExerciseType) : ExerciseDetailUiEvent
    data class OnDailyNoteChanged(val dailyNote: String) : ExerciseDetailUiEvent
    data class OnCaloriesChanged(val caloriesBurn: String) : ExerciseDetailUiEvent
    data class OnExerciseTimeChanged(val minutes: String) : ExerciseDetailUiEvent
    data class OnStepCountChanged(val stepCount: String) : ExerciseDetailUiEvent
    data class OnWeightChanged(val weight: String) : ExerciseDetailUiEvent
    data class OnAddPhotos(val urls: List<String>) : ExerciseDetailUiEvent
    data class OnRemovePhoto(val url: String) : ExerciseDetailUiEvent
    data object OnSaveRecord: ExerciseDetailUiEvent
}

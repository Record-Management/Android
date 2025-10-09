package see.day.exercise.state

import see.day.model.record.exercise.ExerciseRecordInput
import see.day.model.record.exercise.ExerciseType
import see.day.model.time.DateTime
import see.day.model.time.DateTimeFormatter
import see.day.model.time.formatter.KoreanDateTimeFormatter

data class ExerciseDetailUiState(
    val exerciseType: ExerciseType,
    val dailyNote: String,
    val recordDate: DateTimeFormatter,
    val caloriesBurned: String,
    val exerciseTimeMinutes: String,
    val stepCount: String,
    val weight: String,
    val imageUrls: List<String>,
    val editMode: EditMode
) {
    sealed class EditMode {
        data object Create : EditMode()
        data class Edit(val originalRecord: ExerciseRecordInput, val recordId: String) : EditMode()
    }

    val canSubmit: Boolean = if (dailyNote.isEmpty()) {
        false
    } else if (!hasExerciseData()) {
        false
    } else {
        when (editMode) {
            is EditMode.Create -> true
            is EditMode.Edit -> {
                val origin = editMode.originalRecord
                (dailyNote != origin.dailyNote || caloriesBurned != origin.caloriesBurned || exerciseTimeMinutes != origin.exerciseTimeMinutes || stepCount != origin.stepCount)
            }
        }
    }

    private fun hasExerciseData(): Boolean =
        caloriesBurned.isOverZero() || exerciseTimeMinutes.isOverZero() || stepCount.isOverZero()


    private fun String.isOverZero(): Boolean {
        return this.isNotEmpty() && this.toInt() > 0
    }

    companion object {
        val init = ExerciseDetailUiState(
            exerciseType = ExerciseType.GOLF,
            dailyNote = "",
            recordDate = KoreanDateTimeFormatter(DateTime.now(DateTime.korea)),
            caloriesBurned = "",
            exerciseTimeMinutes = "",
            stepCount = "",
            weight = "",
            imageUrls = listOf(),
            editMode = EditMode.Create
        )
    }
}

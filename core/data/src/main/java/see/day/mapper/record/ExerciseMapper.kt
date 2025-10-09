package see.day.mapper.record

import see.day.model.record.exercise.ExerciseRecordInput
import see.day.network.dto.record.exercise.ExerciseRecordInputRequest

fun ExerciseRecordInput.toDto() : ExerciseRecordInputRequest {
    return ExerciseRecordInputRequest(
        exerciseType = exerciseType.name,
        dailyNote = dailyNote,
        recordDate = recordDate.formatDate(),
        recordTime = recordDate.formatTime(),
        caloriesBurned = if(caloriesBurned.isBlank()) null else caloriesBurned.toInt(),
        exerciseTimeMinutes = if(exerciseTimeMinutes.isBlank()) null else exerciseTimeMinutes.toInt(),
        stepCount = if(stepCount.isBlank()) null else stepCount.toInt(),
        weight = if(weight.isBlank()) null else weight.toFloat(),
        imageUrls = imageUrls
    )
}

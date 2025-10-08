package see.day.data.mapper

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import see.day.mapper.record.toDto
import see.day.model.record.exercise.ExerciseRecordInput
import see.day.model.record.exercise.ExerciseType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

class ExerciseMapperTest {

    @Test
    fun givenFullExerciseRecordInput_whenMapping_thenReturnsAllInputs() {
        // given
        val caloriesBurn = "12"
        val exerciseTime = "32"
        val stepCount = "10"
        val weight = "0.5"
        val exerciseRecordInput = ExerciseRecordInput(ExerciseType.GOLF, "",KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), caloriesBurned = caloriesBurn, exerciseTimeMinutes = exerciseTime, stepCount = stepCount, weight = weight, listOf())

        // when
        val request = exerciseRecordInput.toDto()

        // then
        assertNotNull(request.caloriesBurned)
        assertNotNull(request.exerciseTimeMinutes)
        assertNotNull(request.stepCount)
        assertNotNull(request.weight)

        assertEquals(request.caloriesBurned, caloriesBurn.toInt())
        assertEquals(request.exerciseTimeMinutes, exerciseTime.toInt())
        assertEquals(request.stepCount, stepCount.toInt())
        assertEquals(request.weight, weight.toFloat())
    }

    @Test
    fun givenCaloriesBurnedEmpty_whenMapping_thenReturnsNull() {
        // given
        val caloriesBurned = ""
        val exerciseRecordInput = ExerciseRecordInput(ExerciseType.GOLF, "",KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), caloriesBurned = caloriesBurned, exerciseTimeMinutes = "12", stepCount = "123", weight = "0.5", listOf())

        // when
        val request = exerciseRecordInput.toDto()

        // then
        assertNull(request.caloriesBurned)
    }

    @Test
    fun givenCaloriesBurnedBlank_whenMapping_thenReturnsNull() {
        // given
        val caloriesBurned = "  "
        val exerciseRecordInput = ExerciseRecordInput(ExerciseType.GOLF, "",KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), caloriesBurned = caloriesBurned, exerciseTimeMinutes = "12", stepCount = "123", weight = "0.5", listOf())

        // when
        val request = exerciseRecordInput.toDto()

        // then
        assertNull(request.caloriesBurned)

    }
}

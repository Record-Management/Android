package see.day.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import see.day.mapper.record.toRequest
import see.day.model.record.habit.HabitRecordInput
import see.day.model.record.habit.HabitType

class HabitMapperTest {

    @Test
    fun givenFullHabitRecordInput_whenMapping_thenReturnsAllInputs() {
        // given
        val notificationEnabled = true
        val hour = 10
        val minute = 0
        val memo = "hello"
        val habitRecordInput = HabitRecordInput(HabitType.SAVING, notificationEnabled, hour, minute, memo)

        // when
        val request = habitRecordInput.toRequest()

        // then
        assertNotNull(request.notificationTime)
        assertNotNull(request.memo)

        assertEquals(request.notificationTime, "10:00")
    }

    @Test
    fun givenNotificationEnabledFalse_whenMapping_thenNotificationTimeIsNull() {
        // given
        val notificationEnabled = false
        val hour = 10
        val minute = 0
        val habitRecordInput = HabitRecordInput(HabitType.SAVING, notificationEnabled, hour, minute, "")

        // when
        val request = habitRecordInput.toRequest()

        // then
        assertNull(request.notificationTime)
    }

    @Test
    fun givenMemoEmpty_whenMapping_thenMemoNull() {
        // given
        val memo = ""
        val habitRecordInput = HabitRecordInput(HabitType.SAVING,false, 0, 0, memo)

        // when
        val request = habitRecordInput.toRequest()

        // then
        assertNull(request.memo)
    }
}

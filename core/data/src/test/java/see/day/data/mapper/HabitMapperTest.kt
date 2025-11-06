package see.day.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import see.day.mapper.record.toDto
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
        val habitRecordInput = HabitRecordInput(HabitType.SAVING, notificationEnabled, hour, minute, memo,"2024-10-19",false)

        // when
        val request = habitRecordInput.toDto()

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
        val habitRecordInput = HabitRecordInput(HabitType.SAVING, notificationEnabled, hour, minute, "","2024-10-19",false)

        // when
        val request = habitRecordInput.toDto()

        // then
        assertNull(request.notificationTime)
    }

    @Test
    fun givenMemoEmpty_whenMapping_thenMemoNull() {
        // given
        val memo = ""
        val habitRecordInput = HabitRecordInput(HabitType.SAVING,false, 0, 0, memo,"2024-10-19",false)

        // when
        val request = habitRecordInput.toDto()

        // then
        assertNull(request.memo)
    }
}

package see.day.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import see.day.mapper.schedule.toDto
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.ScheduleInput
import see.day.model.schedule.SchedulePaletteColor
import java.time.LocalDate

class ScheduleMapperTest {

    // 모든 입력값이 채워진 일정 입력값을 요청 DTO로 변환할 때 각 필드가 올바르게 매핑되는지 확인한다.
    @Test
    fun givenFullScheduleInput_whenMapping_thenReturnsAllInputs() {
        // given
        val scheduleInput = ScheduleInput(
            title = "매장 점검",
            startDate = LocalDate.of(2026, 3, 1),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = AlertTime.CUSTOM,
            notificationCustomHours = 9,
            notificationCustomMinutes = 30,
            repeatType = RepeatTime.WEEK,
            repeatEndsOn = LocalDate.of(2026, 8, 10),
            location = "도쿄점",
            color = SchedulePaletteColor.ORANGE,
            memo = "오픈 전 냉장고 점검"
        )

        // when
        val request = scheduleInput.toDto()

        // then
        assertEquals("매장 점검", request.title)
        assertEquals("2026-03-01", request.startDate)
        assertEquals("2026-03-21", request.endDate)
        assertEquals("CUSTOM", request.notificationType)
        assertEquals(9, request.notificationCustomHours)
        assertEquals(30, request.notificationCustomMinutes)
        assertEquals("WEEKLY", request.repeatType)
        assertEquals("2026-08-10", request.repeatEndsOn)
        assertEquals("도쿄점", request.location)
        assertEquals("ORANGE", request.color)
        assertEquals("오픈 전 냉장고 점검", request.memo)
    }

    // 알림 타입이 NONE이면 알림 타입 문자열은 NONE으로 변환하고 커스텀 알림 시간은 요청에 포함하지 않는다.
    @Test
    fun givenNotificationTypeNone_whenMapping_thenNotificationTypeIsNoneAndCustomTimeIsNull() {
        // given
        val scheduleInput = ScheduleInput(
            title = "매장 점검",
            startDate = LocalDate.of(2026, 3, 1),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = AlertTime.NONE,
            notificationCustomHours = 9,
            notificationCustomMinutes = 30,
            repeatType = RepeatTime.NONE,
            repeatEndsOn = null,
            location = "도쿄점",
            color = SchedulePaletteColor.ORANGE,
            memo = "오픈 전 냉장고 점검"
        )

        // when
        val request = scheduleInput.toDto()

        // then
        assertEquals("NONE", request.notificationType)
        assertNull(request.notificationCustomHours)
        assertNull(request.notificationCustomMinutes)
    }

    // 알림 타입이 ONE_DAY_BEFORE이면 알림 타입 문자열은 ONE_DAY_BEFORE로 변환하고 커스텀 알림 시간은 요청에 포함하지 않는다.
    @Test
    fun givenNotificationTypeOneDayBefore_whenMapping_thenNotificationTypeIsOneDayBeforeAndCustomTimeIsNull() {
        // given
        val scheduleInput = ScheduleInput(
            title = "매장 점검",
            startDate = LocalDate.of(2026, 3, 1),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = AlertTime.ONE_DAY_BEFORE,
            notificationCustomHours = 9,
            notificationCustomMinutes = 30,
            repeatType = RepeatTime.NONE,
            repeatEndsOn = null,
            location = "도쿄점",
            color = SchedulePaletteColor.ORANGE,
            memo = "오픈 전 냉장고 점검"
        )

        // when
        val request = scheduleInput.toDto()

        // then
        assertEquals("ONE_DAY_BEFORE", request.notificationType)
        assertNull(request.notificationCustomHours)
        assertNull(request.notificationCustomMinutes)
    }

    // 반복 타입이 NONE이면 반복 종료일은 요청에 포함하지 않는다.
    @Test
    fun givenRepeatTypeNone_whenMapping_thenRepeatEndsOnIsNull() {
        // given
        val scheduleInput = ScheduleInput(
            title = "매장 점검",
            startDate = LocalDate.of(2026, 3, 1),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = AlertTime.NONE,
            notificationCustomHours = null,
            notificationCustomMinutes = null,
            repeatType = RepeatTime.NONE,
            repeatEndsOn = LocalDate.of(2026, 8, 10),
            location = "도쿄점",
            color = SchedulePaletteColor.ORANGE,
            memo = "오픈 전 냉장고 점검"
        )

        // when
        val request = scheduleInput.toDto()

        // then
        assertEquals("NONE", request.repeatType)
        assertNull(request.repeatEndsOn)
    }

    // 위치와 메모가 빈 문자열이면 선택 항목으로 처리하기 위해 null로 변환한다.
    @Test
    fun givenLocationAndMemoEmpty_whenMapping_thenLocationAndMemoAreNull() {
        // given
        val scheduleInput = ScheduleInput(
            title = "매장 점검",
            startDate = LocalDate.of(2026, 3, 1),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = AlertTime.NONE,
            notificationCustomHours = null,
            notificationCustomMinutes = null,
            repeatType = RepeatTime.NONE,
            repeatEndsOn = null,
            location = "",
            color = SchedulePaletteColor.ORANGE,
            memo = ""
        )

        // when
        val request = scheduleInput.toDto()

        // then
        assertNull(request.location)
        assertNull(request.memo)
    }
}

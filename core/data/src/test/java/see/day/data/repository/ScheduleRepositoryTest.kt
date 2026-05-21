package see.day.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import see.day.domain.repository.ScheduleRepository
import see.day.mapper.schedule.toDto
import see.day.model.exception.BadRequestException
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.ScheduleInput
import see.day.model.schedule.SchedulePaletteColor
import see.day.network.ScheduleService
import see.day.network.dto.CommonResponse
import see.day.network.dto.schedule.ScheduleResponse
import see.day.network.dto.toResponseBody
import see.day.repository.ScheduleRepositoryImpl
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class ScheduleRepositoryTest {

    private lateinit var sut: ScheduleRepository

    @Mock
    private lateinit var scheduleService: ScheduleService

    @Before
    fun setUp() {
        sut = ScheduleRepositoryImpl(scheduleService)
    }

    // 일정 등록에 성공하면 응답으로 받은 scheduleRecordId를 Result 성공 값으로 반환한다.
    @Test
    fun givenScheduleInput_whenInsertSchedule_thenReturnsScheduleRecordId() {
        runTest {
            // given
            val scheduleInput = createScheduleInput()
            val scheduleRecordId = "1"
            val scheduleResponse = ScheduleResponse(
                scheduleRecordId = scheduleRecordId,
                title = "매장 점검",
                startDate = LocalDate.of(2026, 3, 21),
                endDate = LocalDate.of(2026, 3, 21),
                notificationType = AlertTime.ONE_DAY_BEFORE,
                notificationCustomHours = null,
                notificationCustomMinutes = null,
                repeatType = RepeatTime.NONE,
                repeatEndsOn = null,
                location = "도쿄점",
                color = SchedulePaletteColor.ORANGE,
                memo = "오픈 전 냉장고 점검"
            )

            whenever(scheduleService.postSchedule(scheduleInput.toDto())).thenReturn(scheduleResponse)

            // when
            val result = sut.insertSchedule(scheduleInput).getOrThrow()

            // then
            assertEquals(scheduleRecordId, result)
            verify(scheduleService).postSchedule(scheduleInput.toDto())
        }
    }

    // 일정 등록 중 400 에러가 발생하면 BadRequestException으로 변환하고 에러 메시지를 유지한다.
    @Test
    fun givenBadScheduleInput_whenInsertSchedule_thenThrowsBadRequestException() {
        runTest {
            // given
            val scheduleInput = createScheduleInput(title = "")
            val errorMessage = "잘못된 일정 요청입니다."

            whenever(scheduleService.postSchedule(scheduleInput.toDto())).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(CommonResponse(400, "C400", errorMessage, null))
                    )
                )
            )

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.insertSchedule(scheduleInput)
                        .onFailure {
                            assertEquals(errorMessage, it.message)
                        }.getOrThrow()
                }
            }

            // then
            verify(scheduleService).postSchedule(scheduleInput.toDto())
        }
    }

    // 일정 조회에 성공하면 응답으로 받은 ScheduleResponse를 ScheduleDetail 모델로 변환해서 반환한다.
    @Test
    fun givenScheduleRecordId_whenGetSchedule_thenReturnsScheduleDetail() {
        runTest {
            // given
            val scheduleRecordId = "schedule-record-id"
            val scheduleResponse = ScheduleResponse(
                scheduleRecordId = scheduleRecordId,
                title = "매장 점검",
                startDate = LocalDate.of(2026, 3, 21),
                endDate = LocalDate.of(2026, 3, 21),
                notificationType = AlertTime.CUSTOM,
                notificationCustomHours = 9,
                notificationCustomMinutes = 30,
                repeatType = RepeatTime.NONE,
                repeatEndsOn = LocalDate.of(2026, 8, 10),
                location = "도쿄점",
                color = SchedulePaletteColor.ORANGE,
                memo = "오픈 전 냉장고 점검"
            )

            whenever(scheduleService.getSchedule(scheduleRecordId)).thenReturn(scheduleResponse)

            // when
            val result = sut.getSchedule(scheduleRecordId).getOrThrow()

            // then
            assertEquals(scheduleRecordId, result.scheduleRecordId)
            assertEquals("매장 점검", result.title)
            assertEquals(LocalDate.of(2026, 3, 21), result.startDate)
            assertEquals(LocalDate.of(2026, 3, 21), result.endDate)
            assertEquals(AlertTime.CUSTOM, result.alertType)
            assertEquals(9, result.notificationCustomHours)
            assertEquals(30, result.notificationCustomMinutes)
            assertEquals(RepeatTime.NONE, result.repeatType)
            assertEquals(LocalDate.of(2026, 8, 10), result.repeatEndsOn)
            assertEquals("도쿄점", result.location)
            assertEquals(SchedulePaletteColor.ORANGE, result.color)
            assertEquals("오픈 전 냉장고 점검", result.memo)
            verify(scheduleService).getSchedule(scheduleRecordId)
        }
    }

    // 일정 조회 중 400 에러가 발생하면 BadRequestException으로 변환하고 에러 메시지를 유지한다.
    @Test
    fun givenInvalidScheduleRecordId_whenGetSchedule_thenThrowsBadRequestException() {
        runTest {
            // given
            val scheduleRecordId = "invalid-schedule-record-id"
            val errorMessage = "존재하지 않는 일정입니다."

            whenever(scheduleService.getSchedule(scheduleRecordId)).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(CommonResponse(400, "C400", errorMessage, null))
                    )
                )
            )

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.getSchedule(scheduleRecordId)
                        .onFailure {
                            assertEquals(errorMessage, it.message)
                        }.getOrThrow()
                }
            }

            // then
            verify(scheduleService).getSchedule(scheduleRecordId)
        }
    }

    private fun createScheduleInput(
        title: String = "매장 점검",
        notificationType: AlertTime = AlertTime.ONE_DAY_BEFORE,
        notificationCustomHours: Int? = null,
        notificationCustomMinutes: Int? = null,
        repeatType: RepeatTime = RepeatTime.NONE,
        repeatEndsOn: LocalDate? = null,
    ): ScheduleInput {
        return ScheduleInput(
            title = title,
            startDate = LocalDate.of(2026, 3, 21),
            endDate = LocalDate.of(2026, 3, 21),
            notificationType = notificationType,
            notificationCustomHours = notificationCustomHours,
            notificationCustomMinutes = notificationCustomMinutes,
            repeatType = repeatType,
            repeatEndsOn = repeatEndsOn,
            location = "도쿄점",
            color = SchedulePaletteColor.ORANGE,
            memo = "오픈 전 냉장고 점검"
        )
    }
}

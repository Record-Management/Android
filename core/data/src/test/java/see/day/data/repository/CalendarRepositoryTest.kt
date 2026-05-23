package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.data.response.successCommonResponse
import see.day.domain.repository.CalendarRepository
import see.day.model.record.RecordType
import see.day.network.CalendarService
import see.day.network.dto.calendar.DailyDetailRecordResponse
import see.day.network.dto.calendar.DailyRecordsResponse
import see.day.network.dto.calendar.MonthlyRecordResponse
import see.day.network.dto.record.DailyRecordResponse
import see.day.network.dto.schedule.DailyScheduleResponse
import see.day.model.schedule.SchedulePaletteColor
import see.day.repository.CalendarRepositoryImpl
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class CalendarRepositoryTest {

    private lateinit var sut: CalendarRepository

    @Mock
    private lateinit var calendarService: CalendarService

    @Before
    fun setUp() {
        sut = CalendarRepositoryImpl(calendarService)
    }

    @Test
    fun givenYearAndMonth_whenGetMonthlyRecords_thenReturnsMonthlyRecords() {
        runTest {
            // given
            val year = 2025
            val month = 10
            val types = arrayOf<String>()

            val response = successCommonResponse(
                response = MonthlyRecordResponse(
                    year,
                    month,
                    monthlyRecords = listOf(DailyRecordsResponse(date = "", mainRecordType = RecordType.EXERCISE,records = listOf(see.day.network.dto.calendar.DailyRecordResponse("", "DAILY",true))))
                )
            )
            whenever(calendarService.getMonthlyRecords(year, month, types)).thenReturn(response)

            // when
            val result = sut.getMonthlyRecords(year, month, types).getOrThrow()

            // then
            assertTrue(result.dailyRecords.any { it.records.any { it.type == RecordType.DAILY } })

            verify(calendarService).getMonthlyRecords(year, month, types)
        }
    }

    @Test
    fun givenDate_whenGetDailyDetailRecord_thenReturnsDailyRecords() {
        runTest {
            // given
            val date = "2025-09-17"

            val response = successCommonResponse(
                response = DailyDetailRecordResponse(
                    date = date,
                    records = listOf(DailyRecordResponse("", "DAILY", "", "", "", "", listOf(), "Love", "")),
                    schedules = emptyList()
                )
            )
            whenever(calendarService.getDailyRecords(date)).thenReturn(response)

            // when
            val result = sut.getDailyRecords(date).getOrThrow()

            // then
            println(result.records)
            assertTrue(result.records.any { it.type == RecordType.DAILY })
            assertTrue(result.schedules.isEmpty())

            verify(calendarService).getDailyRecords(date)
        }
    }

    @Test
    fun givenDate_whenGetDailyDetailRecordWithSchedules_thenReturnsRecordsAndSchedules() {
        runTest {
            // given
            val date = "2025-09-17"
            val scheduleResponse = DailyScheduleResponse(
                scheduleId = "schedule-1",
                title = "회의",
                startDate = LocalDate.of(2025, 9, 17),
                endDate = LocalDate.of(2025, 9, 17),
                color = SchedulePaletteColor.RED,
                memo = "중요한 회의"
            )

            val response = successCommonResponse(
                response = DailyDetailRecordResponse(
                    date = date,
                    records = listOf(DailyRecordResponse("", "DAILY", "", "", "", "", listOf(), "Love", "")),
                    schedules = listOf(scheduleResponse)
                )
            )
            whenever(calendarService.getDailyRecords(date)).thenReturn(response)

            // when
            val result = sut.getDailyRecords(date).getOrThrow()

            // then
            assertTrue(result.records.any { it.type == RecordType.DAILY })
            assertTrue(result.schedules.isNotEmpty())
            assertTrue(result.schedules.any { it.title == "회의" })

            verify(calendarService).getDailyRecords(date)
        }
    }

    @Test
    fun givenDate_whenGetDailyDetailRecordWithOnlySchedules_thenReturnsSchedules() {
        runTest {
            // given
            val date = "2025-09-17"
            val scheduleResponse = DailyScheduleResponse(
                scheduleId = "schedule-1",
                title = "약속",
                startDate = LocalDate.of(2025, 9, 17),
                endDate = LocalDate.of(2025, 9, 17),
                color = SchedulePaletteColor.BLUE,
                memo = null
            )

            val response = successCommonResponse(
                response = DailyDetailRecordResponse(
                    date = date,
                    records = emptyList(),
                    schedules = listOf(scheduleResponse)
                )
            )
            whenever(calendarService.getDailyRecords(date)).thenReturn(response)

            // when
            val result = sut.getDailyRecords(date).getOrThrow()

            // then
            assertTrue(result.records.isEmpty())
            assertTrue(result.schedules.isNotEmpty())
            assertTrue(result.schedules.any { it.title == "약속" })

            verify(calendarService).getDailyRecords(date)
        }
    }
}

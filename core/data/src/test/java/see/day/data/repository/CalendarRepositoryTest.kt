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
import see.day.repository.CalendarRepositoryImpl

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
                    monthlyRecords = listOf(DailyRecordsResponse(date = "", records = listOf(see.day.network.dto.calendar.DailyRecordResponse("", "DAILY"))))
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
                    records = listOf(DailyRecordResponse("", "DAILY", "", "", "", "", listOf(), "Love", ""))
                )
            )
            whenever(calendarService.getDailyRecordData(date)).thenReturn(response)

            // when
            val result = sut.getDailyDetailRecords(date).getOrThrow()

            // then
            println(result.records)
            assertTrue(result.records.any { it.type == RecordType.DAILY })

            verify(calendarService).getDailyRecordData(date)
        }
    }
}

package see.day.data.api.calendarService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.calendarService.json.getDetailRecordsResponse
import see.day.data.api.calendarService.json.getDetailRecordsWithSchedulesResponse
import see.day.data.api.calendarService.json.getDetailRecordsWithOnlySchedulesResponse
import see.day.data.api.calendarService.json.getMonthlyRecordResponse
import see.day.network.CalendarService
import see.day.network.dto.record.DailyRecordResponse
import see.day.network.dto.record.ExerciseRecordResponse

class CalendarMonthlyTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: CalendarService
    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiTestUtils.httpLoggingInterceptor)
            .build()

        val retrofit = createRetrofit(
            baseUrl = mockWebServer.url("/"),
            client = okHttpClient
        )

        sut = retrofit.create(CalendarService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenYearAndMonth_whenGetMonthlyRecord_thenWorksFine() = runTest {
        // given
        val year = 2025
        val month = 1
        val types = arrayOf<String>()

        val responseJson = getMonthlyRecordResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.getMonthlyRecords(year, month, types)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/calendar/$year/$month", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("캘린더가 성공적으로 조회되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertEquals(year, response.data?.year)
        assertEquals(month, response.data?.month)
    }

    @Test
    fun givenDate_whenGetDetailRecord_thenWorksFine() = runTest {
        // given
        val date = "2025-01-07"

        val responseJson = getDetailRecordsResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.getDailyRecords(date)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/records/date/$date", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("일일 기록이 성공적으로 조회되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertEquals(1,response.data?.records?.filterIsInstance<DailyRecordResponse>()?.size ?: 0)
        assertEquals(1,response.data?.records?.filterIsInstance<ExerciseRecordResponse>()?.size ?: 0)
        assertEquals(date, response.data?.date)
        assertEquals(0, response.data?.schedules?.size ?: -1)
    }

    @Test
    fun givenDate_whenGetDetailRecordWithSchedules_thenWorksFine() = runTest {
        // given
        val date = "2025-01-07"

        val responseJson = getDetailRecordsWithSchedulesResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.getDailyRecords(date)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/records/date/$date", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("일일 기록이 성공적으로 조회되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertEquals(date, response.data?.date)
        
        // records 검증
        assertEquals(1, response.data?.records?.size ?: 0)
        assertEquals(1, response.data?.records?.filterIsInstance<DailyRecordResponse>()?.size ?: 0)
        
        // schedules 검증
        assertEquals(2, response.data?.schedules?.size ?: 0)
        assertEquals("schedule-001", response.data?.schedules?.get(0)?.scheduleId)
        assertEquals("팀 회의", response.data?.schedules?.get(0)?.title)
        assertEquals("분기 계획 논의", response.data?.schedules?.get(0)?.memo)
        assertEquals("schedule-002", response.data?.schedules?.get(1)?.scheduleId)
        assertEquals("점심 약속", response.data?.schedules?.get(1)?.title)
        assertEquals(null, response.data?.schedules?.get(1)?.memo)
    }

    @Test
    fun givenDate_whenGetDetailRecordWithOnlySchedules_thenWorksFine() = runTest {
        // given
        val date = "2025-01-07"

        val responseJson = getDetailRecordsWithOnlySchedulesResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.getDailyRecords(date)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/records/date/$date", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("일일 기록이 성공적으로 조회되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertEquals(date, response.data?.date)
        
        // records 검증 - 빈 리스트
        assertEquals(0, response.data?.records?.size ?: -1)
        
        // schedules 검증
        assertEquals(1, response.data?.schedules?.size ?: 0)
        assertEquals("schedule-003", response.data?.schedules?.get(0)?.scheduleId)
        assertEquals("병원 예약", response.data?.schedules?.get(0)?.title)
        assertEquals("오후 3시", response.data?.schedules?.get(0)?.memo)
    }
}

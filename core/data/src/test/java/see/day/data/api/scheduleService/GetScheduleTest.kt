package see.day.data.api.scheduleService

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.scheduleService.json.getScheduleBadRequestResponse
import see.day.data.api.scheduleService.json.getScheduleFullFieldSuccessResponse
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.SchedulePaletteColor
import see.day.network.ScheduleService
import java.time.LocalDate

class GetScheduleTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: ScheduleService
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

        sut = retrofit.create(ScheduleService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenScheduleRecordId_whenGettingSchedule_thenReturnsSchedule() = runTest {
        // given
        val scheduleRecordId = "schedule-record-id"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getScheduleFullFieldSuccessResponse)
        )

        // when
        val response = sut.getSchedule(scheduleRecordId)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/schedule-records/$scheduleRecordId", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)

        // 응답 body 검증
        // userId, createdAt, updatedAt은 DTO에 없지만 ignoreUnknownKeys 설정으로 무시된다.
        assertEquals(scheduleRecordId, response.scheduleRecordId)
        assertEquals("매장 점검", response.title)
        assertEquals(LocalDate.of(2026, 3, 21), response.startDate)
        assertEquals(LocalDate.of(2026, 3, 21), response.endDate)
        assertEquals(AlertTime.CUSTOM, response.notificationType)
        assertEquals(9, response.notificationCustomHours)
        assertEquals(30, response.notificationCustomMinutes)
        assertEquals(RepeatTime.NONE, response.repeatType)
        assertEquals(LocalDate.of(2026, 8, 10), response.repeatEndsOn)
        assertEquals("도쿄점", response.location)
        assertEquals(SchedulePaletteColor.ORANGE, response.color)
        assertEquals("오픈 전 냉장고 점검", response.memo)
    }

    @Test
    fun givenInvalidScheduleRecordId_whenGettingSchedule_thenThrowsHttpException() = runTest {
        // given
        val scheduleRecordId = "invalid-schedule-record-id"

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(getScheduleBadRequestResponse)
        )

        // when
        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                sut.getSchedule(scheduleRecordId)
            }
        }

        // then
        assertEquals(400, exception.code())
    }
}

package see.day.data.api.scheduleService

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.scheduleService.json.postScheduleBadRequestResponse
import see.day.data.api.scheduleService.json.postScheduleCustomNotificationSuccessResponse
import see.day.data.api.scheduleService.json.postScheduleFullFieldSuccessResponse
import see.day.data.api.scheduleService.json.postScheduleRequiredFieldSuccessResponse
import see.day.model.schedule.AlertTime
import see.day.model.schedule.RepeatTime
import see.day.model.schedule.SchedulePaletteColor
import see.day.network.ScheduleService
import see.day.network.dto.schedule.ScheduleInputRequest

class PostScheduleTest {

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
    fun givenFullFieldScheduleInput_whenPosting_thenReturnsSchedule() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "매장 점검",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "ONE_DAY_BEFORE",
            notificationCustomHours = 9,
            notificationCustomMinutes = 30,
            repeatType = "NONE",
            repeatEndsOn = "2026-08-10",
            location = "도쿄점",
            color = "ORANGE",
            memo = "오픈 전 냉장고 점검"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(postScheduleFullFieldSuccessResponse)
        )

        // when
        val response = sut.postSchedule(scheduleInputRequest)
        val recordedRequest = mockWebServer.takeRequest()
        val requestBody = recordedRequest.body.readUtf8()

        // then
        // 요청 검증
        assertEquals("/api/schedule-records", recordedRequest.path)
        assertEquals("POST", recordedRequest.method)

        // 응답 body 검증
        assertEquals("schedule-record-id", response.scheduleRecordId)
        assertEquals("매장 점검", response.title)
        assertEquals(AlertTime.ONE_DAY_BEFORE, response.notificationType)
        assertEquals(9, response.notificationCustomHours)
        assertEquals(30, response.notificationCustomMinutes)
        assertEquals(RepeatTime.NONE, response.repeatType)
        assertEquals(SchedulePaletteColor.ORANGE, response.color)
        assertEquals("도쿄점", response.location)
        assertEquals("오픈 전 냉장고 점검", response.memo)
    }

    @Test
    fun givenRequiredFieldScheduleInput_whenPosting_thenReturnsScheduleWithOptionalFieldsNull() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "매장 점검",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "NONE",
            notificationCustomHours = null,
            notificationCustomMinutes = null,
            repeatType = "NONE",
            repeatEndsOn = null,
            color = "ORANGE"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(postScheduleRequiredFieldSuccessResponse)
        )

        // when
        val response = sut.postSchedule(scheduleInputRequest)
        val recordedRequest = mockWebServer.takeRequest()
        val requestBody = recordedRequest.body.readUtf8()

        // then
        // 요청 검증
        assertEquals("/api/schedule-records", recordedRequest.path)
        assertFalse(requestBody.contains("notificationCustomHours"))
        assertFalse(requestBody.contains("notificationCustomMinutes"))
        assertFalse(requestBody.contains("repeatEndsOn"))
        assertFalse(requestBody.contains("location"))
        assertFalse(requestBody.contains("memo"))

        // 응답 body 검증
        assertEquals("schedule-record-id", response.scheduleRecordId)
        assertEquals(AlertTime.NONE, response.notificationType)
        assertNull(response.notificationCustomHours)
        assertNull(response.notificationCustomMinutes)
        assertEquals(RepeatTime.NONE, response.repeatType)
        assertNull(response.repeatEndsOn)
        assertNull(response.location)
        assertNull(response.memo)
    }

    @Test
    fun givenCustomNotificationBoundaryScheduleInput_whenPosting_thenReturnsCustomNotificationSchedule() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "매장 점검",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "CUSTOM",
            notificationCustomHours = 23,
            notificationCustomMinutes = 59,
            repeatType = "NONE",
            repeatEndsOn = null,
            color = "ORANGE"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(postScheduleCustomNotificationSuccessResponse)
        )

        // when
        val response = sut.postSchedule(scheduleInputRequest)
        val recordedRequest = mockWebServer.takeRequest()
        val requestBody = recordedRequest.body.readUtf8()

        // then
        // 요청 검증
        assertEquals("/api/schedule-records", recordedRequest.path)
        assertEquals(true, requestBody.contains("\"notificationType\":\"CUSTOM\""))
        assertEquals(true, requestBody.contains("\"notificationCustomHours\":23"))
        assertEquals(true, requestBody.contains("\"notificationCustomMinutes\":59"))

        // 응답 body 검증
        assertEquals(AlertTime.CUSTOM, response.notificationType)
        assertEquals(23, response.notificationCustomHours)
        assertEquals(59, response.notificationCustomMinutes)
    }

    @Test
    fun givenInvalidCustomNotificationHours_whenPosting_thenThrowsHttpException() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "매장 점검",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "CUSTOM",
            notificationCustomHours = 24,
            notificationCustomMinutes = 30,
            repeatType = "NONE",
            repeatEndsOn = null,
            color = "ORANGE"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(postScheduleBadRequestResponse)
        )

        // when
        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                sut.postSchedule(scheduleInputRequest)
            }
        }

        // then
        assertEquals(400, exception.code())
    }

    @Test
    fun givenInvalidCustomNotificationMinutes_whenPosting_thenThrowsHttpException() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "매장 점검",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "CUSTOM",
            notificationCustomHours = 9,
            notificationCustomMinutes = 60,
            repeatType = "NONE",
            repeatEndsOn = null,
            color = "ORANGE"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(postScheduleBadRequestResponse)
        )

        // when
        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                sut.postSchedule(scheduleInputRequest)
            }
        }

        // then
        assertEquals(400, exception.code())
    }

    @Test
    fun givenEmptyTitle_whenPosting_thenThrowsHttpException() = runTest {
        // given
        val scheduleInputRequest = ScheduleInputRequest(
            title = "",
            startDate = "2026-03-21",
            endDate = "2026-03-21",
            notificationType = "NONE",
            notificationCustomHours = null,
            notificationCustomMinutes = null,
            repeatType = "NONE",
            repeatEndsOn = null,
            color = "ORANGE"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(postScheduleBadRequestResponse)
        )

        // when
        val exception = assertThrows(HttpException::class.java) {
            runBlocking {
                sut.postSchedule(scheduleInputRequest)
            }
        }

        // then
        assertEquals(400, exception.code())
    }
}

package see.day.data.api.habitRecordService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.habitRecordService.json.postFullFieldSuccessResponse
import see.day.data.api.habitRecordService.json.postNotificationTimeEmptyFieldSuccessResponse
import see.day.network.ExerciseRecordService
import see.day.network.HabitRecordService
import see.day.network.dto.record.habit.HabitRecordInputRequest

class PostHabitRecordTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: HabitRecordService
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

        sut = retrofit.create(HabitRecordService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenHabitRecordInput_whenPosting_thenReturnsFullHabitRecord() = runTest {
        // given
        val habitRecordInputRequest = HabitRecordInputRequest("EXERCISE", true, "09:00", "오늘도 운동 완료!","2024-10-19")
        val responseJson = postFullFieldSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.postHabitRecord(habitRecordInputRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/habit-records", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("정상적으로 처리되었습니다.", response.message)
        assertEquals("S20000", response.code)
        assertNotNull(response.data)
        assertEquals(response.data?.notificationTime, "09:00:00")
    }

    @Test
    fun givenHabitRecordInputEmptyNotificationTime_whenPosting_thenReturnsNotificationTimeEmpty() = runTest {
        // given
        val habitRecordInputRequest = HabitRecordInputRequest("EXERCISE", false, null, "오늘도 운동 완료!","2024-10-19")
        val responseJson = postNotificationTimeEmptyFieldSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.postHabitRecord(habitRecordInputRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/habit-records", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("정상적으로 처리되었습니다.", response.message)
        assertEquals("S20000", response.code)
        assertNotNull(response.data)
        assertNull(response.data?.notificationTime)
        assertEquals(response.data?.notificationEnabled, false)
    }
}

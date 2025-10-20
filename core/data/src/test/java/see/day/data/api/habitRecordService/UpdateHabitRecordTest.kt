package see.day.data.api.habitRecordService

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
import see.day.data.api.habitRecordService.json.updateHabitRecordSuccessResponse
import see.day.network.HabitRecordService
import see.day.network.dto.record.habit.HabitRecordEditRequest

class UpdateHabitRecordTest {

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
    fun givenRecordIdAndHabitRecordEditForm_whenUpdating_thenWorksFine() = runTest {
        // given
        val recordId = "123-123"
        val updateHabitRecordEditRequest = HabitRecordEditRequest("WATER_DRINKING", true, "09:00","오늘도 물 2L 마시기 성공!")
        val responseJson = updateHabitRecordSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateHabitRecord(recordId, updateHabitRecordEditRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/habit-records/$recordId", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("정상적으로 처리되었습니다.", response.message)
        assertEquals("S20000", response.code)
        assertNotNull(response.data)
        assertEquals(response.data?.memo, updateHabitRecordEditRequest.memo)
    }
}

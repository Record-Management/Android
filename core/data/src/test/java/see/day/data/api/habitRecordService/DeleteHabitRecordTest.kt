package see.day.data.api.habitRecordService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.habitRecordService.json.deleteHabitSuccessResponse
import see.day.network.HabitRecordService

class DeleteHabitRecordTest {

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
    fun givenRecordId_whenDeleting_thenWorksFine() = runTest {
        // given
        val recordId = "123123-123"
        val responseBody = deleteHabitSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // when
        val response = sut.deleteHabitRecord(recordId)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/habit-records/$recordId",recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("정상적으로 처리되었습니다.",response.message)
        assertEquals("S20000",response.code)
        assertNull(response.data)
    }
}

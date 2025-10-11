package see.day.data.api.dailyRecordService

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
import see.day.data.api.dailyRecordService.json.deleteDailyRecordSuccessResponse
import see.day.network.DailyRecordService

class DeleteDailyRecordTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: DailyRecordService
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

        sut = retrofit.create(DailyRecordService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenDailyRecordId_whenDeleting_thenWorksFine() = runTest {
        // given
        val recordId = "1231dasda"
        val responseJson = deleteDailyRecordSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.deleteDailyRecord(recordId)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/daily-records/${recordId}", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("하루 기록이 성공적으로 삭제되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNull(response.data)
    }
}

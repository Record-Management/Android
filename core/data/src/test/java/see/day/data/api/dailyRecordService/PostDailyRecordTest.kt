package see.day.data.api.dailyRecordService

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.dailyRecordService.json.dailyRecordDetailOverResponse
import see.day.data.api.dailyRecordService.json.dailyRecordDetailResponse
import see.day.mapper.record.toDto
import see.day.model.exception.BadRequestException
import see.day.model.record.daily.CreateDailyRecord
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter
import see.day.network.DailyRecordService
import see.day.utils.ErrorUtils.createResult

class PostDailyRecordTest {

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
    fun givenCreateDailyRecord_whenPost_thenReturnsDetailDailyRecord() = runTest {
        // given
        val createDailyRecord = CreateDailyRecord("", "", KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), imageUrls = listOf())
        val responseJson = dailyRecordDetailResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody(responseJson)
        )

        // when
        val response = sut.postDailyRecord(createDailyRecord.toDto().toRequestBody())
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/daily-records", recordedRequest.path)

        // 응답 body 검증
        assertEquals(201, response.statusCode)
        assertEquals("하루 기록이 성공적으로 작성되었습니다", response.message)
        assertEquals("S201", response.code)
        assertNotNull(response.data)
    }

    @Test
    fun givenOverCreateDailyRecord_whenPost_thenThrows400Exception() = runTest {
        // given
        val createDailyRecord = CreateDailyRecord("", "", KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), imageUrls = listOf())
        val responseJson = dailyRecordDetailOverResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(responseJson)
        )

        // when
        assertThrows(BadRequestException::class.java) {
            runBlocking {
                createResult {
                    sut.postDailyRecord(createDailyRecord.toDto().toRequestBody())
                }.onFailure {
                    assertEquals("하루에 등록할 수 있는 일상 기록은 최대 2개입니다.", it.message)
                }.getOrThrow()
            }
        }
    }
}

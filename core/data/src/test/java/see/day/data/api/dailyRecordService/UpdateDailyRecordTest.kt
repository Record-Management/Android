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
import see.day.data.api.dailyRecordService.json.dailyRecordUpdateFailure400Response
import see.day.data.api.dailyRecordService.json.dailyRecordUpdateFailure404Response
import see.day.data.api.dailyRecordService.json.dailyRecordUpdateSuccessResponse
import see.day.model.exception.BadRequestException
import see.day.model.exception.NotFoundException
import see.day.network.DailyRecordService
import see.day.network.dto.record.daily.DailyRecordEditRequest
import see.day.utils.ErrorUtils.createResult

class UpdateDailyRecordTest {

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
    fun givenEditDailyRecord_whenUpdate_thenWorksFine() = runTest{
        // given
        val dailyRecordEdit = DailyRecordEditRequest("","", listOf())
        val recordId = "asdasd"
        val responseJson = dailyRecordUpdateSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateDailyRecord(recordId,dailyRecordEdit)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/daily-records/$recordId", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("하루 기록이 성공적으로 수정되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
    }

    @Test
    fun givenWrongEditDailyRecord_whenUpdate_thenThrows400Exception() = runTest {
        // given
        val dailyRecordEdit = DailyRecordEditRequest("이상하다 이상해","Wrong", listOf())
        val recordId = "asdasd"
        val responseJson = dailyRecordUpdateFailure400Response

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(responseJson)
        )

        // when
        assertThrows(BadRequestException::class.java) {
            runBlocking {
                createResult {
                    sut.updateDailyRecord(recordId,dailyRecordEdit)
                }.onFailure {
                    assertEquals("입력값을 확인해주세요.", it.message)
                }.getOrThrow()
            }
        }
    }

    @Test
    fun givenNotFoundDailyRecord_whenUpdate_thenThrows400Exception() = runTest {
        // given
        val dailyRecordEdit = DailyRecordEditRequest("이상하다 이상해","Wrong", listOf())
        val recordId = "NotFound"
        val responseJson = dailyRecordUpdateFailure404Response

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(responseJson)
        )

        // when
        assertThrows(NotFoundException::class.java) {
            runBlocking {
                createResult {
                    sut.updateDailyRecord(recordId,dailyRecordEdit)
                }.onFailure {
                    assertEquals("존재하지 않는 기록입니다.", it.message)
                }.getOrThrow()
            }
        }
    }
}

package see.day.data.api.goalService

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
import see.day.data.api.goalService.json.getCurrentPeriodIsNullSuccessResponse
import see.day.data.api.goalService.json.getCurrentPeriodSuccessResponse
import see.day.network.GoalService

class GetGoalReportTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: GoalService
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

        sut = retrofit.create(GoalService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun given_whenGetGoalReport_thenHasCurrentReport() = runTest {
        // given
        val responseBody = getCurrentPeriodSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // when
        val response = sut.getGoalReport()
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/goals/achievement/report",recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("목표 달성 보고서 조회가 성공적으로 완료되었습니다",response.message)
        assertEquals("S200",response.code)
        assertNotNull(response.data?.currentPeriod)
    }

    @Test
    fun given_whenGetGoalReport_thenHasNoCurrentReport() = runTest {
        // given
        val responseBody = getCurrentPeriodIsNullSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // when
        val response = sut.getGoalReport()
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/goals/achievement/report",recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("목표 달성 보고서 조회가 성공적으로 완료되었습니다",response.message)
        assertEquals("S200",response.code)
        assertNotNull(response.data)
        assertNull(response.data?.currentPeriod)
    }
}

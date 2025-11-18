package see.day.data.api.goalService

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
import see.day.network.GoalService
import see.day.network.dto.goal.NewGoalRequest

class PostNewGoalTest {

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
    fun givenNewGoalRequest_whenPostNewGoal_thenWorksFine() = runTest {
        // given
        val newGoalRequest = NewGoalRequest("HABIT",30)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""
                {
                  "statusCode": 200,
                  "code": "S200",
                  "message": "새로운 목표가 성공적으로 생성되었습니다",
                  "data": {
                    "goalId": "550e8400-e29b-41d4-a716-446655440010",
                    "recordType": "HABIT",
                    "goalDays": 30,
                    "startDate": [2025,11,10],
                    "endDate": [2025,12,09],
                    "message": "목표가 성공적으로 생성되었습니다."
                  }
                }
                """.trimIndent())
        )

        // when
        val response = sut.postNewGoal(newGoalRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/goals/new",recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("새로운 목표가 성공적으로 생성되었습니다",response.message)
        assertEquals("S200",response.code)
        assertNotNull(response.data)
    }
}

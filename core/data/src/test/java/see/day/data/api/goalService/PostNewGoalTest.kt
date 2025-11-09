package see.day.data.api.goalService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.model.goal.NewGoal
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
        val userId = "Asdasd"
        val newGoalRequest = NewGoalRequest("HABIT",30,"2025-11-08")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""
                    {
                      "goalId": "550e8400-e29b-41d4-a716-446655440010",
                      "recordType": "HABIT",
                      "goalDays": 30,
                      "startDate": "2025-11-05",
                      "endDate": "2025-12-04",
                      "message": "목표가 성공적으로 생성되었습니다."
                    }
                """.trimIndent())
        )

        // when
        val response = sut.postNewGoal(userId, newGoalRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/goals/new",recordedRequest.path)

        // 추후 CommonResponse가 탑재되면 변경
    }
}

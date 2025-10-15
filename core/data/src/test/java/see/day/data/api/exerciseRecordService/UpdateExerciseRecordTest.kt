package see.day.data.api.exerciseRecordService

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
import see.day.data.api.exerciseRecordService.json.postFullFieldSuccessResponse
import see.day.data.api.exerciseRecordService.json.updateExerciseRequestSuccessResponse
import see.day.network.ExerciseRecordService
import see.day.network.dto.record.exercise.ExerciseRecordEditRequest

class UpdateExerciseRecordTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: ExerciseRecordService
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

        sut = retrofit.create(ExerciseRecordService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenExerciseRecordEditForm_whenUpdating_thenWorksFine() = runTest {
        // given
        val exerciseRecordEdit = ExerciseRecordEditRequest("RUNNING", 100, 100, 100, 10.4f, "", listOf(), "")
        val responseJson = updateExerciseRequestSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateExerciseRecord("123", exerciseRecordEdit)
        val recordedResponse = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/exercise-records/123",recordedResponse.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("운동기록이 성공적으로 수정되었습니다",response.message)
        assertEquals("S200",response.code)
        assertNotNull(response.data)
    }
}

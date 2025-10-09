package see.day.data.api.exerciseRecordService

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.exerciseRecordService.json.postCaloriesNullFieldSuccessResponse
import see.day.data.api.exerciseRecordService.json.postFullFieldSuccessResponse
import see.day.data.api.exerciseRecordService.json.postOverLimitExerciseRecordFailureResponse
import see.day.data.api.exerciseRecordService.json.postOverLimitRecordsFailureResponse
import see.day.model.exception.BadRequestException
import see.day.network.DailyRecordService
import see.day.network.ExerciseRecordService
import see.day.network.dto.record.exercise.ExerciseRecordInputRequest
import see.day.utils.ErrorUtils.createResult

class PostExerciseRecordTest {

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
    fun givenExerciseRecordInput_whenPosting_thenReturns() = runTest{
        // given
        val exerciseRecordInput = ExerciseRecordInputRequest("RUNNING","happy", "2025-10-09","15:30",100,100,100,100.5f, listOf())
        val responseJson = postFullFieldSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody(responseJson)
        )

        // when
        val response = sut.postExerciseRecord(exerciseRecordInput)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/exercise-records",recordedRequest.path)

        // 응답 body 검증
        assertEquals(201, response.statusCode)
        assertEquals("운동기록이 성공적으로 작성되었습니다",response.message)
        assertEquals("S201",response.code)
        assertNotNull(response.data)
    }

    @Test
    fun givenExerciseRecordInputWithCaloriesNull_whenPosting_thenReturns() = runTest{
        // given
        val exerciseRecordInput = ExerciseRecordInputRequest("RUNNING","happy", "2025-10-09","15:30",null,100,100,100.5f, listOf())
        val responseJson = postCaloriesNullFieldSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody(responseJson)
        )

        // when
        val response = sut.postExerciseRecord(exerciseRecordInput)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/exercise-records",recordedRequest.path)

        // 응답 body 검증
        assertEquals(201, response.statusCode)
        assertEquals("운동기록이 성공적으로 작성되었습니다",response.message)
        assertEquals("S201",response.code)
        assertNotNull(response.data)
        assertNull(response.data?.caloriesBurned)
    }

    @Test
    fun givenExerciseRecordInputOverLimit_whenPosting_thenThrows400Exception() = runTest{
        // given
        val exerciseRecordInput = ExerciseRecordInputRequest("RUNNING","happy", "2025-10-09","15:30",null,100,100,100.5f, listOf())
        val responseJson = postOverLimitExerciseRecordFailureResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(responseJson)
        )

        // when
        assertThrows(BadRequestException::class.java) {
            runBlocking {
                createResult {
                    sut.postExerciseRecord(exerciseRecordInput)
                }.onFailure {
                    assertEquals("하루에 등록할 수 있는 운동 기록은 최대 1개입니다.",it.message)
                }.getOrThrow()
            }
        }
    }

    @Test
    fun givenExerciseRecordInputOverLimitRecords_whenPosting_thenThrows400Exception() = runTest{
        // given
        val exerciseRecordInput = ExerciseRecordInputRequest("RUNNING","happy", "2025-10-09","15:30",null,100,100,100.5f, listOf())
        val responseJson = postOverLimitRecordsFailureResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody(responseJson)
        )

        // when
        assertThrows(BadRequestException::class.java) {
            runBlocking {
                createResult {
                    sut.postExerciseRecord(exerciseRecordInput)
                }.onFailure {
                    assertEquals("하루에 등록할 수 있는 기록 종류는 최대 2가지입니다.",it.message)
                }.getOrThrow()
            }
        }
    }
}

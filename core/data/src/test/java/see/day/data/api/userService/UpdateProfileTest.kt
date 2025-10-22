package see.day.data.api.userService

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
import see.day.data.api.userService.json.updateUserBirthDateSuccessResponse
import see.day.data.api.userService.json.updateUserNicknameSuccessResponse
import see.day.network.UserService
import see.day.network.dto.user.UserProfileChangedInputRequest

class UpdateProfileTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: UserService
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

        sut = retrofit.create(UserService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenNicknameChanged_whenUpdating_thenWorksFine() = runTest {
        // given
        val nickname = "변경된닉네임"
        val userProfileChangedInputRequest = UserProfileChangedInputRequest(nickname = nickname, birthDate = null)
        val responseJson = updateUserNicknameSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateUserProfile(userProfileChangedInputRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/users/profile", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("요청이 성공적으로 처리되었습니다.", response.message)
        assertEquals("SUCCESS", response.code)
        assertNotNull(response.data)
        assertEquals(response.data?.nickname, nickname)
    }

    @Test
    fun givenBirthDateChanged_whenUpdating_thenWorksFine() = runTest {
        // given
        val birthDate = "2000-01-16"
        val userProfileChangedInputRequest = UserProfileChangedInputRequest(nickname = null, birthDate = birthDate)
        val responseJson = updateUserBirthDateSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateUserProfile(userProfileChangedInputRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/users/profile", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("요청이 성공적으로 처리되었습니다.", response.message)
        assertEquals("SUCCESS", response.code)
        assertNotNull(response.data)
        assertEquals(response.data?.birthDate, birthDate)
    }
}

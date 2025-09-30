package see.day.data.api.authService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.authService.json.logoutSuccessResponse
import see.day.network.AuthService
import see.day.network.dto.auth.LogoutRequest

class AuthServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: AuthService
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

        sut = retrofit.create(AuthService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenRefreshToken_whenLogout_thenWorksFine() = runTest {
        // given
        val refreshToken = "asdasd"
        val allDevices = false
        val request = LogoutRequest(refreshToken, allDevices)
        val responseJson = logoutSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.logout(request.toRequestBody())
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        Assert.assertEquals("/api/auth/logout", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("로그아웃되었습니다.", response.message)
        assertEquals("S200", response.code)
    }

}

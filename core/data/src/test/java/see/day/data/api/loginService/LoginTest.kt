package see.day.data.api.loginService

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.loginService.json.createLoginResponse
import see.day.data.api.loginService.json.oldLoginResponse
import see.day.network.LoginService
import see.day.network.dto.login.LoginRequest

class LoginTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: LoginService
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

        sut = retrofit.create(LoginService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenNewbieKakaoLogin_whenRequestLogin_thenReturnsTokens() = runBlocking {
        // given
        val socialType = "kakao"
        val accessToken = "Asdadejwlk23k4j1"

        val loginRequest = LoginRequest(socialType, accessToken)

        val responseJson = createLoginResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.signIn(loginRequest.toRequestBody())
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/auth/social-login", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("요청이 성공적으로 처리되었습니다.", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertTrue(response.data?.isNewUser ?: false)
    }

    @Test
    fun givenKakaoLogin_whenRequestLogin_thenReturnsTokens() = runBlocking {
        // given
        val socialType = "kakao"
        val accessToken = "Asdadejwlk23k4j1"

        val loginRequest = LoginRequest(socialType, accessToken)

        val responseJson = oldLoginResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody(responseJson)
        )

        // when
        val response = sut.signIn(loginRequest.toRequestBody())
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/auth/social-login", recordedRequest.path)

        // 응답 body 검증
        assertEquals(201, response.statusCode)
        assertEquals("요청이 성공적으로 처리되었습니다.", response.message)
        assertEquals("S201", response.code)
        assertNotNull(response.data)
        assertFalse(response.data?.isNewUser ?: true)
    }
}

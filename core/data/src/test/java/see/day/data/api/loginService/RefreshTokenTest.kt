package see.day.data.api.loginService

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
import see.day.data.api.loginService.json.refreshJson
import see.day.network.AuthService
import see.day.network.dto.auth.RefreshTokenRequest

class RefreshTokenTest {

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
    fun givenRefreshToken_whenRequestAccessToken_thenWorksFine() = runTest {
        // given
        val refreshToken = RefreshTokenRequest("dasdasdasd")

        val responseJson = refreshJson

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.refresh(refreshToken.toRequestBody())
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/auth/refresh", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("요청이 성공적으로 처리되었습니다.", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)

        assertEquals("new_token", response.data?.accessToken ?: "")
    }
}

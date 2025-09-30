package see.day.data.api.error

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import see.day.model.exception.NetworkErrorException
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.error.json.errorResponse
import see.day.network.AuthService
import see.day.network.dto.login.LoginRequest
import see.day.utils.ErrorUtils.createResult

class ErrorTest {

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
    fun givenLoginRequest_whenNetworkError_thenThrows500Exception() = runTest {
        // given
        val socialType = "kakao"
        val accessToken = "Asdadejwlk23k4j1"

        val loginRequest = LoginRequest(socialType, accessToken)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(errorResponse)
        )

        // when
        Assert.assertThrows(NetworkErrorException::class.java) {
            runBlocking {
                createResult {
                    sut.signIn(loginRequest.toRequestBody())
                }.onFailure {
                    assertEquals("서버 내부 오류가 발생했습니다.", it.message)
                }.getOrThrow()
            }
        }
        Unit
    }
}

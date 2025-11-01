package see.day.data.api.userService

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
import see.day.data.api.userService.json.updateFcmTokenSuccessResponse
import see.day.network.UserService
import see.day.network.dto.user.FcmTokenRequest

class UpdateFcmTokenTest {

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
    fun givenFcmToken_whenUpdating_thenWorksFine() = runTest {
        // given
        val fcmTokenRequest = FcmTokenRequest("asdasdad")
        val responseJson = updateFcmTokenSuccessResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.updateFcmToken(fcmTokenRequest)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/users/fcm-token", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("FCM 토큰이 성공적으로 업데이트되었습니다", response.message)
        assertEquals("U200", response.code)
        assertNull(response.data)
    }
}

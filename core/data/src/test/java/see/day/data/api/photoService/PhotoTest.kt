package see.day.data.api.photoService

import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.photoService.json.postPhotoResponse
import see.day.network.PhotoService

class PhotoTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: PhotoService
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

        sut = retrofit.create(PhotoService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun given_whenInsertPhoto_thenWorksFine() = runTest {
        // given
        val responseJson = postPhotoResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )

        // when
        val response = sut.uploadPhotos(
            listOf(
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file", // 서버에서 받는 파라미터 이름
                        "test.jpg", // 파일 이름
                        "dummy content".toRequestBody("image/jpeg".toMediaType()) // 더미 파일 내용
                    )
                    .build().part(0)
            )

        )
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // then
        // 요청 검증
        assertEquals("/api/files/upload", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("파일이 성공적으로 업로드되었습니다.", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
    }
}

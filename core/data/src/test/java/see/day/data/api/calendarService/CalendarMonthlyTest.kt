package see.day.data.api.calendarService

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import see.day.data.api.ApiTestUtils
import see.day.data.api.ApiTestUtils.createRetrofit
import see.day.data.api.calendarService.json.calendarGetResponse
import see.day.network.CalendarService

class CalendarMonthlyTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var sut: CalendarService
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

        sut = retrofit.create(CalendarService::class.java)
    }

    @Test
    fun givenYearAndMonth_whenGetCalendarInfo_thenWorksFine() = runTest{
        // given
        val year = 2025
        val month = 1
        val types = arrayOf<String>()

        val responseJson = calendarGetResponse

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseJson)
        )
        // when
        val response = sut.getMonthlyRecords(year,month,types)
        val recordedRequest = mockWebServer.takeRequest()

        // then
        // 요청 검증
        assertEquals("/api/records/calendar/$year/$month", recordedRequest.path)

        // 응답 body 검증
        assertEquals(200, response.statusCode)
        assertEquals("캘린더가 성공적으로 조회되었습니다", response.message)
        assertEquals("S200", response.code)
        assertNotNull(response.data)
        assertEquals(year, response.data?.year)
        assertEquals(month, response.data?.month)

    }
}

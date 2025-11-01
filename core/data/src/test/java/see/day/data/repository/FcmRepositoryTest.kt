package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.domain.repository.FcmRepository
import see.day.network.fcm.FcmTokenProvider
import see.day.repository.FcmRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class FcmRepositoryTest {

    private lateinit var fcmRepository : FcmRepository

    @Mock
    private lateinit var fcmTokenProvider: FcmTokenProvider

    @Before
    fun setUp() {
        fcmRepository = FcmRepositoryImpl(fcmTokenProvider)
    }

    @Test
    fun given_whenGetFcmToken_thenWorksFine() {
        runTest {
            // given
            val fcmToken = "asdasd"
            whenever(fcmTokenProvider.getToken()).thenReturn(fcmToken)

            // when
            val result = fcmRepository.getFcmToken().getOrThrow()

            // then
            assertEquals(result,fcmToken)

            verify(fcmTokenProvider).getToken()
        }
    }
}

package see.day.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import see.day.domain.repository.UserRepository
import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.network.UserService
import see.day.network.dto.CommonResponse
import see.day.network.dto.user.FullUserResponse
import see.day.repository.UserRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var sut: UserRepository

    @Mock
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        sut = UserRepositoryImpl(userService)
    }

    @Test
    fun givenOnboardingInfo_whenPostOnboarding_thenWorksFine() {
        runTest {
            // given
            val request = OnboardingComplete(
                "",
                RecordType.HABIT,
                "",
                10,
                true
            )
            val response = CommonResponse(
                200,
                "S200",
                "요청이 성공적으로 처리되었습니다.",
                FullUserResponse(
                    id = "",
                    name = "",
                    nickname = "",
                    email = "",
                    socialType = "",
                    mainRecordType = "",
                    birthDate = "",
                    goalDays = 10,
                    notificationEnabled = true,
                    onboardingCompleted = true,
                    createdAt = ""
                )
            )

            whenever(userService.postOnboardComplete(any())).thenReturn(
                response
            )

            // when
            val result = sut.onboardComplete(request).getOrThrow()

            // then
            verify(userService).postOnboardComplete(any())
        }
    }

    @Test
    fun given_whenGetUser_thenReturnsUserInfo() {
        runTest {
            // given
            val recordType = RecordType.DAILY
            val response = CommonResponse(
                200,
                "S200",
                "요청이 성공적으로 처리되었습니다.",
                FullUserResponse(
                    id = "",
                    name = "",
                    nickname = "",
                    email = "",
                    socialType = "",
                    mainRecordType = recordType.name,
                    birthDate = "",
                    goalDays = 10,
                    notificationEnabled = true,
                    onboardingCompleted = true,
                    createdAt = ""
                )
            )

            whenever(userService.getUser()).thenReturn(response)

            // when
            val result = sut.getMainRecordType().getOrThrow()

            // then
            assertEquals(recordType, result)
            verify(userService).getUser()
        }
    }
}

package see.day.data.repository

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.UserRepository
import see.day.mapper.toDto
import see.day.model.exception.BadRequestException
import see.day.model.goal.TreeStage
import see.day.model.login.SocialType
import see.day.model.record.RecordType
import see.day.model.user.OnboardingComplete
import see.day.model.user.UserProfileChangedInput
import see.day.network.UserService
import see.day.network.dto.CommonResponse
import see.day.network.dto.auth.DeleteUserRequest
import see.day.network.dto.auth.LogoutRequest
import see.day.network.dto.toResponseBody
import see.day.network.dto.user.FcmTokenRequest
import see.day.network.dto.user.FullUserResponse
import see.day.repository.UserRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    private lateinit var sut: UserRepository

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var dataSource: DataStoreDataSource

    @Before
    fun setUp() {
        sut = UserRepositoryImpl(userService, dataSource)
    }

    @Test
    fun givenOnboardingInfo_whenPostOnboarding_thenWorksFine() {
        runTest {
            // given
            val request = OnboardingComplete(
                "",
                RecordType.HABIT,
                "",
                10
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
                    mainRecordType = RecordType.HABIT,
                    birthDate = "",
                    goalDays = 10,
                    onboardingCompleted = true,
                    createdAt = "",
                    treeStage = TreeStage.STAGE_1
                )
            )

            whenever(userService.postOnboardComplete(request.toDto())).thenReturn(
                response
            )

            // when
            val result = sut.onboardComplete(request).getOrThrow()

            // then
            verify(userService).postOnboardComplete(request.toDto())
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
                    mainRecordType = recordType,
                    birthDate = "",
                    goalDays = 10,
                    onboardingCompleted = true,
                    createdAt = "",
                    treeStage = TreeStage.STAGE_1
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

    @Test
    fun given_whenDeleteUser_thenClearData() {
        runTest {
            // given
            val deleteUserRequest = DeleteUserRequest("테스트용도")
            whenever(userService.deleteUser(deleteUserRequest)).thenReturn(Unit)
            whenever(dataSource.clearData()).thenReturn(Unit)

            // when
            val result = sut.deleteUser().getOrThrow()

            // then
            verify(userService).deleteUser(deleteUserRequest)
            verify(dataSource).clearData()
        }
    }

    @Test
    fun given_whenDeleteUserThrowException_thenClearData() {
        runTest {
            // given
            val deleteUserRequest = DeleteUserRequest("테스트용도")
            whenever(userService.deleteUser(deleteUserRequest)).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(CommonResponse(400, "E40001", "잘못된 입력 값입니다.", null))
                    )
                )
            )
            whenever(dataSource.clearData()).thenReturn(Unit)

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.deleteUser().getOrThrow()
                }
            }

            // then
            verify(userService).deleteUser(deleteUserRequest)
            verify(dataSource).clearData()
        }
    }

    @Test
    fun given_whenGetUser_thenWorksFine() {
        runTest {
            // given
            val userResponse = FullUserResponse("", "", "", "", SocialType.KAKAO.toString(), RecordType.HABIT, "", 20, true, "", TreeStage.STAGE_1)
            whenever(userService.getUser()).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "요청이 성공적으로 처리되었습니다.",
                    userResponse
                )
            )

            // when
            val result = sut.getUser().getOrThrow()

            // then
            verify(userService).getUser()
        }
    }

    @Test
    fun givenUserProfileNickname_whenUpdate_thenWorksFine() {
        runTest {
            // given
            val nickname = "변경된닉네임"
            val userProfileChangedInput = UserProfileChangedInput.ofNickname(nickname)
            val userResponse = FullUserResponse("", "", nickname, "", SocialType.KAKAO.toString(), RecordType.HABIT, "", 20, true, "",TreeStage.STAGE_1)

            whenever(userService.updateUserProfile(userProfileChangedInput.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "요청이 성공적으로 처리되었습니다.",
                    userResponse
                )
            )

            // when
            val result = sut.updateUser(updateUserProfileChangedInput = userProfileChangedInput).getOrThrow()

            // then
            assertEquals(result.nickname, nickname)

            verify(userService).updateUserProfile(userProfileChangedInput.toDto())
        }
    }

    @Test
    fun givenUserProfileBirthdate_whenUpdate_thenWorksFine() {
        runTest {
            // given
            val birthDate = "2000-01-16"
            val userProfileChangedInput = UserProfileChangedInput.ofBirthDate(birthDate)
            val userResponse = FullUserResponse("", "", "", "", SocialType.KAKAO.toString(), RecordType.HABIT, "2000-01-16", 20, true, "",TreeStage.STAGE_1)

            whenever(userService.updateUserProfile(userProfileChangedInput.toDto())).thenReturn(
                CommonResponse(
                    200,
                    "SUCCESS",
                    "요청이 성공적으로 처리되었습니다.",
                    userResponse
                )
            )

            // when
            val result = sut.updateUser(updateUserProfileChangedInput = userProfileChangedInput).getOrThrow()

            // then
            assertEquals(result.birthDate, birthDate)

            verify(userService).updateUserProfile(userProfileChangedInput.toDto())
        }
    }

    @Test
    fun givenRefreshToken_whenLogout_thenTokensClear() {
        runTest {
            // given
            val refreshToken = "asdasdasd"
            val allDevices = false
            val logoutRequest = LogoutRequest(refreshToken, allDevices)

            whenever(dataSource.getRefreshToken()).thenReturn(flowOf(refreshToken))
            whenever(userService.logout(logoutRequest)).thenReturn(CommonResponse(200, "S200", "로그아웃되었습니다.", null))
            whenever(dataSource.clearData()).thenReturn(Unit)

            // when
            val result = sut.logout(allDevices).getOrThrow()

            // then
            verify(dataSource).getRefreshToken()
            verify(userService).logout(logoutRequest)
            verify(dataSource).clearData()
        }
    }

    @Test
    fun givenFcmToken_whenUpdating_thenWorksFine() {
        runTest {
            // given
            val fcmToken = "asdasdasd"
            whenever(dataSource.hasToken()).thenReturn(flowOf(true))
            whenever(userService.updateFcmToken(FcmTokenRequest(fcmToken))).thenReturn(CommonResponse(200, "U200", "FCM 토큰이 성공적으로 업데이트되었습니다", null))

            // when
            val result = sut.updateFcmToken(fcmToken).getOrThrow()

            // then
            verify(dataSource).hasToken()
            verify(userService).updateFcmToken(FcmTokenRequest(fcmToken))
        }
    }

    @Test
    fun givenFcmTokenAndHasNotToken_whenUpdating_thenNotUpdating() {
        runTest {
            // given
            val fcmToken = "asdasd"
            whenever(dataSource.hasToken()).thenReturn(flowOf(false))

            // when
            val result = sut.updateFcmToken(fcmToken).getOrThrow()

            // then
            verify(dataSource).hasToken()
        }
    }
}

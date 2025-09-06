package see.day.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
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
import see.day.model.exception.BadRequestException
import see.day.model.login.SocialLogin
import see.day.model.login.SocialType
import retrofit2.HttpException
import retrofit2.Response
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.LoginRepository
import see.day.model.navigation.AppStartState.HOME
import see.day.model.navigation.AppStartState.ONBOARDING
import see.day.network.LoginService
import see.day.network.dto.CommonResponse
import see.day.network.dto.common.UserDto
import see.day.network.dto.login.LoginResponse
import see.day.network.dto.toResponseBody
import see.day.repository.LoginRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {

    private lateinit var sut: LoginRepository

    @Mock
    private lateinit var loginService: LoginService

    @Mock
    private lateinit var dataSource: DataStoreDataSource

    @Before
    fun setUp() {
        sut = LoginRepositoryImpl(dataSource, loginService)
    }

    @Test
    fun givenNewSocialLogin_whenLogin_thenReturnsTrue() {
        runTest {
            // given
            val newSocialLogin = SocialLogin(SocialType.KAKAO, "asdasdasdasds")
            val accessToken = "Asdasda"
            val refreshToken = "asdijasdlkajs"

            whenever(loginService.signIn(any())).thenReturn(
                CommonResponse(
                    200,
                    "S200",
                    "요청이 성공적으로 처리되었습니다.",
                    LoginResponse(accessToken, refreshToken, UserDto("", "", "", "", "", false), isNewUser = true)
                )
            )
            whenever(dataSource.saveAccessToken(accessToken)).thenReturn(Unit)
            whenever(dataSource.saveRefreshToken(refreshToken)).thenReturn(Unit)
            whenever(dataSource.saveAppStartState(ONBOARDING)).thenReturn(Unit)

            // when
            val result = sut.login(newSocialLogin).getOrThrow()

            // then
            assertEquals(ONBOARDING, result)

            verify(dataSource).saveAccessToken(accessToken)
            verify(dataSource).saveRefreshToken(refreshToken)
            verify(dataSource).saveAppStartState(ONBOARDING)
            verify(loginService).signIn(any())
        }
    }

    @Test
    fun givenOldSocialLogin_whenLogin_thenReturnsFalse() {
        runTest {
            // given
            val oldSocialLogin = SocialLogin(SocialType.KAKAO, "asdasdasdasds")
            val accessToken = "Asdasda"
            val refreshToken = "asdijasdlkajs"

            whenever(loginService.signIn(any())).thenReturn(
                CommonResponse(
                    201,
                    "S201",
                    "요청이 성공적으로 처리되었습니다.",
                    LoginResponse(accessToken, refreshToken, UserDto("", "", "", "", "", true), isNewUser = false)
                )
            )
            whenever(dataSource.saveAccessToken(accessToken)).thenReturn(Unit)
            whenever(dataSource.saveRefreshToken(refreshToken)).thenReturn(Unit)
            whenever(dataSource.saveAppStartState(HOME)).thenReturn(Unit)

            // when
            val result = sut.login(oldSocialLogin).getOrThrow()

            // then
            assertEquals(HOME, result)

            verify(dataSource).saveAccessToken(accessToken)
            verify(dataSource).saveRefreshToken(refreshToken)
            verify(dataSource).saveAppStartState(HOME)
            verify(loginService).signIn(any())
        }
    }

    @Test
    fun givenIncorrectAccessToken_whenLogin_thenReturns400Exception() {
        runTest {
            // given
            val oldSocialLogin = SocialLogin(SocialType.KAKAO, "Incorrect")

            whenever(loginService.signIn(any())).thenThrow(
                HttpException(
                    Response.error<Any?>(
                        400,
                        toResponseBody<Unit?>(CommonResponse(400, "E40001", "잘못된 입력 값입니다.", null))
                    )
                )
            )

            // when
            assertThrows(BadRequestException::class.java) {
                runBlocking {
                    sut.login(oldSocialLogin)
                        .onFailure {
                            assertEquals("잘못된 입력 값입니다.", it.message)
                        }.getOrThrow()
                }
            }

            // then
            verify(loginService).signIn(any())
        }
    }
}

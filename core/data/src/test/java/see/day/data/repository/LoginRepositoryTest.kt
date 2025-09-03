package see.day.data.repository

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import record.daily.model.exception.BadRequestException
import record.daily.model.login.SocialLogin
import record.daily.model.login.SocialType
import retrofit2.HttpException
import retrofit2.Response
import see.day.datastore.DataStoreDataSource
import see.day.domain.repository.LoginRepository
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
                    LoginResponse(accessToken, refreshToken, UserDto("", "", ""), isNewUser = true)
                )
            )
            whenever(dataSource.saveAccessToken(accessToken)).thenReturn(Unit)
            whenever(dataSource.saveRefreshToken(refreshToken)).thenReturn(Unit)

            // when
            val result = sut.login(newSocialLogin).getOrThrow()

            // then
            assertTrue(result)

            verify(dataSource).saveAccessToken(accessToken)
            verify(dataSource).saveRefreshToken(refreshToken)
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
                    LoginResponse(accessToken, refreshToken, UserDto("", "", ""), isNewUser = false)
                )
            )
            whenever(dataSource.saveAccessToken(accessToken)).thenReturn(Unit)
            whenever(dataSource.saveRefreshToken(refreshToken)).thenReturn(Unit)

            // when
            val result = sut.login(oldSocialLogin).getOrThrow()

            // then
            assertFalse(result)

            verify(dataSource).saveAccessToken(accessToken)
            verify(dataSource).saveRefreshToken(refreshToken)
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

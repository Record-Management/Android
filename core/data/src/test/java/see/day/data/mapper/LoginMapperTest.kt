package see.day.data.mapper

import org.junit.Assert
import org.junit.Test
import record.daily.model.login.SocialLogin
import record.daily.model.login.SocialType
import see.day.mapper.toDto

class LoginMapperTest {

    @Test
    fun givenKakaoSocialLogin_whenMapLoginRequest_thenSocialTypeToLowercase() {
        // given
        val kakaoSocialLogin = SocialLogin(SocialType.KAKAO, "asdasdasd")

        // when
        val loginRequest = kakaoSocialLogin.toDto()

        // then
        Assert.assertEquals(loginRequest.socialType, kakaoSocialLogin.socialType.name)
    }
}

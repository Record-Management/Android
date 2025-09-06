package see.day.mapper

import see.day.model.login.SocialLogin
import see.day.network.dto.login.LoginRequest

fun SocialLogin.toDto(): LoginRequest {
    return LoginRequest(
        socialType = socialType.name,
        accessToken = accessToken
    )
}

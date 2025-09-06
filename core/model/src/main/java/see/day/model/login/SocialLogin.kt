package see.day.model.login

data class SocialLogin(
    val socialType: SocialType,
    val accessToken: String,
)

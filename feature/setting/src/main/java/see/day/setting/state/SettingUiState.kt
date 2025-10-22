package see.day.setting.state

import see.day.model.login.SocialType

data class SettingUiState(
    val id: String,
    val nickname: String,
    val birthday: String,
    val socialType: SocialType
) {

    companion object {
        val init = SettingUiState(
            "",
            "",
            "",
            SocialType.KAKAO
        )
    }
}

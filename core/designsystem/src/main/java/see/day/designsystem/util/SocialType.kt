package see.day.designsystem.util

import see.day.designsystem.R
import see.day.model.login.SocialType

fun SocialType.getIconRes() : Int {
    return when(this) {
        SocialType.KAKAO -> R.drawable.ic_kakao
    }
}

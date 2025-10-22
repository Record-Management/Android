package see.day.model.user

data class UserProfileChangedInput(
    val nickname: String?,
    val birthDate: String?
) {
    companion object {
        fun ofNickname(nickname: String) = UserProfileChangedInput(
            nickname = nickname,
            birthDate = null
        )

        fun ofBirthDate(birthDate: String) = UserProfileChangedInput(
            nickname = null,
            birthDate = birthDate
        )
    }
}

package see.day.model.navigation

enum class AppStartState {
    LOGIN,
    ONBOARDING,
    HOME;

    companion object {
        fun fromString(value: String?): AppStartState {
            if (value == null) {
                return LOGIN
            }
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                LOGIN
            }
        }
    }
}

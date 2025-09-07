package see.day.onboarding.state

import see.day.onboarding.R

enum class OnboardingScreenState(val titleRes: Int, val iconRes: Int) {
    RECORD(R.string.record_message, R.drawable.onboard_record),
    NICKNAME(R.string.nickname_message, R.drawable.onboard_nickname),
    BIRTHDAY(R.string.birthday_message, R.drawable.onboard_birthday),
    GOAL(R.string.goals_message, R.drawable.onboard_goal),
    ALERT(R.string.alert_message, R.drawable.onboard_alert)
}

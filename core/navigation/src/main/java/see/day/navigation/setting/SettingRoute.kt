package see.day.navigation.setting

import kotlinx.serialization.Serializable

sealed interface SettingRoute {

    @Serializable
    data object Setting

    @Serializable
    data object SettingGoalNotification

    @Serializable
    data object SettingRecordNotification
}


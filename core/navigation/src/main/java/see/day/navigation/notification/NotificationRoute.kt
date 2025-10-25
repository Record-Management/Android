package see.day.navigation.notification

import kotlinx.serialization.Serializable

sealed interface NotificationRoute {

    @Serializable
    data object History: NotificationRoute
}

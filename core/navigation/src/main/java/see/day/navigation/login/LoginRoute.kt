package see.day.navigation.login

import kotlinx.serialization.Serializable

sealed interface LoginRoute {
    @Serializable data object Login
    @Serializable data object Permission
}



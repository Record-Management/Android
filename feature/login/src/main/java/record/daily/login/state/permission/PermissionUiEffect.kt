package record.daily.login.state.permission

sealed interface PermissionUiEffect {
    data object OnGoLogin : PermissionUiEffect
}

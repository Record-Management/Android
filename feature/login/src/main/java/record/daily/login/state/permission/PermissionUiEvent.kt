package record.daily.login.state.permission

sealed interface PermissionUiEvent {
    data object OnClickPermissionConfirm : PermissionUiEvent
}

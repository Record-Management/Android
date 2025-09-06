package record.daily.login.state.login

interface LoginUiEffect {
    data object GoOnboarding : LoginUiEffect
    data object GoMain : LoginUiEffect
}

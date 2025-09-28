package see.day.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import see.day.designsystem.theme.SeeDayTheme
import see.day.main.navigation.SeedayApp
import see.day.main.viewmodel.MainViewModel
import see.day.model.navigation.AppStartState
import see.day.navigation.home.Home
import see.day.navigation.login.Login
import see.day.navigation.onboarding.OnboardingRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }

        setContent {
            var appStartDestination by rememberSaveable { mutableStateOf<AppStartDestination?>(null) }
            LaunchedEffect(Unit) {
                viewModel.uiEffect.collect { appStartState ->
                    appStartDestination = when (appStartState) {
                        AppStartState.LOGIN -> AppStartDestination.Login
                        AppStartState.HOME -> {
                            if (appStartDestination == null) {
                                AppStartDestination.Home
                            } else {
                                appStartDestination
                            }
                        }
                        AppStartState.ONBOARDING -> {
                            if (appStartDestination == null) {
                                AppStartDestination.Onboarding
                            } else {
                                appStartDestination
                            }
                        }
                    }
                }
            }

            SeeDayTheme {
                appStartDestination?.let { destination ->
                    splashScreen.setKeepOnScreenCondition { false }
                    val appStartRoute = when(destination) {
                        AppStartDestination.Login -> Login
                        AppStartDestination.Home -> Home
                        AppStartDestination.Onboarding -> OnboardingRoute.Onboarding
                    }
                    SeedayApp(appStartDestination = appStartRoute)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClickLogin: () -> Unit) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClickLogin) {
            Text("로그인 화면으로 이동")
        }
    }
}

enum class AppStartDestination {
    Login, Home, Onboarding
}

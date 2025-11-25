package see.day.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import see.day.designsystem.theme.SeeDayTheme
import see.day.main.navigation.SeedayApp
import see.day.main.navigation.rememberNavigationState
import see.day.main.viewmodel.MainViewModel
import see.day.model.navigation.AppStartState
import see.day.navigation.home.Home
import see.day.navigation.login.LoginRoute
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
            val navigationState = rememberNavigationState()
            var startDestination by rememberSaveable { mutableStateOf<AppStartState?>(null) }

            LaunchedEffect(Unit) {
                viewModel.startDestination.collect { destination ->
                    if (destination == null) return@collect
                    startDestination = destination
                    splashScreen.setKeepOnScreenCondition { false }
                }
            }

            SeeDayTheme {
                startDestination?.let { appStartState ->
                    val appStartRoute = when (appStartState) {
                        AppStartState.LOGIN -> LoginRoute.Login
                        AppStartState.HOME -> Home
                        AppStartState.ONBOARDING -> OnboardingRoute.Onboarding
                    }

                    SeedayApp(
                        navigationState = navigationState,
                        viewModel = viewModel,
                        appStartDestination = appStartRoute
                    )
                }
            }
        }
    }
}

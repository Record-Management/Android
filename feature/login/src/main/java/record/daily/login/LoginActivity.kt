package record.daily.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import record.daily.login.screen.LoginScreenRoot
import see.day.designsystem.theme.SeeDayTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeeDayTheme {
                LoginScreenRoot()
            }
        }
    }
}

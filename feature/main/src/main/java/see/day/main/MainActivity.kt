package see.day.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import see.day.designsystem.theme.SeeDayTheme
import see.day.main.navigation.SeedayApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SeeDayTheme {
                SeedayApp()
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

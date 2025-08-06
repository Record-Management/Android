package see.day.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import record.daily.login.LoginActivity
import see.day.designsystem.theme.SeeDayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onClickLoginActivity: () -> Unit = {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        setContent {
            SeeDayTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onClickLogin = onClickLoginActivity,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
) {
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SeeDayTheme {
        Greeting("Android", onClickLogin = {})
    }
}

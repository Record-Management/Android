package see.day.home.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "HOME"

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavigation() {
    composable(HOME_ROUTE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(300.dp))
            Text(modifier = Modifier.fillMaxSize(), text = "여기까지 스프린트 A", textAlign = TextAlign.Center)
        }
    }
}

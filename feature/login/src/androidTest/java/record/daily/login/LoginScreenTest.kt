package record.daily.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test
import record.daily.login.screen.LoginScreen
import see.day.designsystem.theme.SeeDayTheme

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun given_when_shownSplashImage_kakaoLogin() {
        // given & when
        composeTestRule.setContent {
            SeeDayTheme {
                LoginScreen(onClickKakaoLogin = {})
            }
        }

        // shown
        composeTestRule
            .onNodeWithContentDescription("splash_title")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("splash_image")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("kakao_login")
            .assertIsDisplayed()

    }
}

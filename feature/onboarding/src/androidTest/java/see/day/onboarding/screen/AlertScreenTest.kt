package see.day.onboarding.screen

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.onboarding.R
import see.day.onboarding.state.OnboardingScreenState.ALERT
import see.day.onboarding.state.onboarding.OnboardingUiState

class AlertScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun givenInitOnboarding_whenScreening_shownRecordTitleAndBody() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = ALERT)
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.alert_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("온보딩 ${uiState.onboardingScreenState.ordinal} 번째 아이콘")
            .assertIsDisplayed()
    }
}

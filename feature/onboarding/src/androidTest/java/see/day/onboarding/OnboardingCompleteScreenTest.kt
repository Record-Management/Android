package see.day.onboarding

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.onboarding.screen.OnboardingCompleteScreen

class OnboardingCompleteScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownTexts() {
        composeTestRule.setContent {
            OnboardingCompleteScreen { }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.onboard_complete_title_message))
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(700)

        composeTestRule
            .onNodeWithText(context.getString(R.string.onboard_complete_label_1))
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(700)

        composeTestRule
            .onNodeWithText(context.getString(R.string.onboard_complete_label_2))
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(700)

        composeTestRule
            .onNodeWithText(context.getString(R.string.onboard_complete_label_3))
            .assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(700)

        composeTestRule
            .onNodeWithText("시작하기")
            .assertIsDisplayed()
    }
}

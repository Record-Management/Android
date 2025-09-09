package see.day.onboarding.screen

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.onboarding.R
import see.day.onboarding.state.OnboardingScreenState.GOAL
import see.day.onboarding.state.onboarding.OnboardingUiState

class GoalsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun givenGoalsScreen_whenScreening_shownDescriptionMessage() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = GOAL)
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.goals_10_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.goals_20_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.goals_30_message))
            .assertIsDisplayed()
    }

    @Test
    fun givenGoalsScreen_whenTouchDays_shownEnableNextButton() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = GOAL)
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        val goalDays = listOf(10, 20, 30).map { "${it}일" }

        composeTestRule
            .onNodeWithText("다음")
            .assertIsNotEnabled()

        goalDays.forEach { days ->
            composeTestRule
                .onNodeWithText(days)
                .performClick()

            composeTestRule
                .onNodeWithText("다음")
                .assertIsEnabled()
        }
    }

    @Test
    fun givenGoalsScreen_whenTouchDaysDouble_shownDisEnableNextButton() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = GOAL)
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        val goalDays = listOf(10, 20, 30).map { "${it}일" }

        goalDays.forEach { days ->
            composeTestRule
                .onNodeWithText(days)
                .performClick()

            composeTestRule
                .onNodeWithText(days)
                .performClick()

            composeTestRule
                .onNodeWithText("다음")
                .assertIsNotEnabled()
        }
    }
}

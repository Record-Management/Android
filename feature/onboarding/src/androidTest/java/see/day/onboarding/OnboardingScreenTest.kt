package see.day.onboarding

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.onboarding.screen.OnboardingScreen
import see.day.onboarding.state.OnboardingScreenState
import see.day.onboarding.state.OnboardingScreenState.ALERT
import see.day.onboarding.state.OnboardingScreenState.GOAL
import see.day.onboarding.state.onboarding.OnboardingUiEvent
import see.day.onboarding.state.onboarding.OnboardingUiState

class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun givenAlarmScreen_whenClickBackButton_shownGoalsScreen() {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(OnboardingUiState.init.copy(onboardingScreenState = ALERT)) }
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {
                    uiState = uiState.copy(
                        onboardingScreenState = GOAL
                    )
                }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.alert_message))

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.back_button_desc))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.goals_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.alert_message))
            .assertIsNotDisplayed()
    }

    @Test
    fun givenAlarmScreen_whenClickBackButton_shownBeforeScreen() {
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(OnboardingUiState.init.copy(onboardingScreenState = ALERT)) }
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {
                    if (it is OnboardingUiEvent.OnClickBack) {
                        uiState = uiState.copy(
                            onboardingScreenState = OnboardingScreenState.entries[uiState.onboardingScreenState.ordinal - 1]
                        )
                    }
                }
            )
        }

        for (i in 0 until messageList.size - 1) {
            composeTestRule
                .onNodeWithText(context.getString(messageList[i]))
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithContentDescription(context.getString(R.string.back_button_desc))
                .performClick()

            composeTestRule
                .onNodeWithText(context.getString(messageList[i + 1]))
                .assertIsDisplayed()
        }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.back_button_desc))
            .assertIsNotDisplayed()
    }

    val messageList = listOf(
        R.string.alert_message,
        R.string.goals_message,
        R.string.birthday_message,
        R.string.nickname_message,
        R.string.record_message
    )
}

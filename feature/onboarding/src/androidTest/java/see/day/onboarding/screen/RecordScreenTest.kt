package see.day.onboarding.screen

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.model.record.RecordType
import see.day.onboarding.R
import see.day.onboarding.state.onboarding.OnboardingUiState

class RecordScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun givenInitOnboarding_whenScreening_shownRecordTitleAndBody() {
        val uiState = OnboardingUiState.init
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = OnboardingUiState.init,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("온보딩 ${uiState.onboardingScreenState.ordinal} 번째 아이콘")
            .assertIsDisplayed()
    }

    @Test
    fun given_whenClickAllTypes_shownEnabledNextButton() {
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = OnboardingUiState.init,
                uiEvent = {}
            )
        }

        RecordType.entries.forEach { type ->
            composeTestRule
                .onNodeWithContentDescription("$type Image")
                .performClick()

            composeTestRule
                .onNodeWithContentDescription("checked")
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithText("다음")
                .assertIsEnabled()
        }
    }

    @Test
    fun given_whenClickDouble_shownDisabledNextButton() {
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = OnboardingUiState.init,
                uiEvent = {}
            )
        }

        RecordType.entries.forEach { type ->
            composeTestRule
                .onNodeWithContentDescription("$type Image")
                .performClick()

            composeTestRule
                .onNodeWithContentDescription("$type Image")
                .performClick()

            composeTestRule
                .onNodeWithContentDescription("checked")
                .assertIsNotDisplayed()

            composeTestRule
                .onNodeWithText("다음")
                .assertIsNotEnabled()
        }
    }
}

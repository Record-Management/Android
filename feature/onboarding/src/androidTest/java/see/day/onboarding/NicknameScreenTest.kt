package see.day.onboarding

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.onboarding.screen.OnboardingScreen
import see.day.onboarding.state.OnboardingScreenState.NICKNAME
import see.day.onboarding.state.onboarding.OnboardingUiState

class NicknameScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun givenEmptyNickname_whenScreening_shownEnableNextButton() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = NICKNAME)
        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.nickname_hint))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("다음")
            .assertIsNotEnabled()
    }

    @Test
    fun givenCorrectNickname_whenScreening_shownEnableNextButton() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = NICKNAME, nickname = "good")

        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText("good")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("다음")
            .assertIsEnabled()
    }

    @Test
    fun givenIncorrectNickname_whenScreening_shownDisableNextButton() {
        val uiState = OnboardingUiState.init.copy(onboardingScreenState = NICKNAME, nickname = "@@")

        composeTestRule.setContent {
            OnboardingScreen(
                uiState = uiState,
                uiEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText("@@")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("다음")
            .assertIsNotEnabled()
    }
}

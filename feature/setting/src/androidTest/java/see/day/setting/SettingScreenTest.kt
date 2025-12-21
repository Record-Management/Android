package see.day.setting

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.setting.screen.SettingScreen
import see.day.setting.state.SettingUiState

class SettingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownNicknameAndBirthday() {
        composeTestRule.setContent {
            SettingScreen(
                uiState = SettingUiState.init,
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.nickname))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.birthday))
            .assertIsDisplayed()
    }

    @Test
    fun given_whenTouchNickname_shownSettingNicknameBottomSheet() {
        composeTestRule.setContent {
            SettingScreen(
                uiState = SettingUiState.init,
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.nickname))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_nickname))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("종료 버튼")
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_nickname))
            .assertIsNotDisplayed()
    }

    @Test
    fun given_whenTouchBirthday_shownBirthSettingDialog() {
        composeTestRule.setContent {
            SettingScreen(
                uiState = SettingUiState.init.copy(birthDate = "2000-01-16"),
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.birthday))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_birthdate))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.cancel))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_birthdate))
            .assertIsNotDisplayed()
    }

    @Test
    fun given_whenClickLogout_shownLogoutDialog() {
        composeTestRule.setContent {
            SettingScreen(
                uiState = SettingUiState.init,
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.logout))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.logout_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.logout_cancel))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.logout_title))
            .assertIsNotDisplayed()
    }

    @Test
    fun given_whenClickWithdrawal_shownWithdrawalDialog() {
        composeTestRule.setContent {
            SettingScreen(
                uiState = SettingUiState.init,
                onAction = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.withdrawal))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.withdrawal_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.withdrawal_cancel))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.withdrawal_title))
            .assertIsNotDisplayed()
    }
}

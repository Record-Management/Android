package see.day.setting

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.setting.screen.SettingGoalNotificationScreen
import see.day.setting.state.goal.GoalNotificationUiState

class SettingGoalNotificationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownGoalNotificationSettingText() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    SettingGoalNotificationScreen(
                        uiState = GoalNotificationUiState.init,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.goal_notification_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.goal_notification_body))
            .assertIsDisplayed()
    }
}

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
import see.day.setting.screen.SettingRecordNotificationScreen
import see.day.setting.state.record.RecordNotificationUiState

class SettingRecordNotificationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownRecordAllNotificationText() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    SettingRecordNotificationScreen(
                        uiState = RecordNotificationUiState.init,
                        uiEvent = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_all_notification))
            .assertIsDisplayed()
    }

    @Test
    fun given_whenScreening_shownRecordNotificationTexts() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    SettingRecordNotificationScreen(
                        uiState = RecordNotificationUiState.init,
                        uiEvent = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.daily_record_notification))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.exercise_record_notification))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.habit_record_notification))
            .assertIsDisplayed()
    }
}

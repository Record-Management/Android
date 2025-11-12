package see.day.notification

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType
import see.day.notification.screen.NotificationScreen
import see.day.notification.state.NotificationHistoryUiModel
import see.day.notification.state.NotificationUiState

class NotificationScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownEmptyScreen() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    NotificationScreen(
                        uiState = NotificationUiState.init,
                        uiEvent = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.notification_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.empty_history_description))
            .assertIsDisplayed()
    }

    @Test
    fun givenDummyNotificationData_whenScreening_shownHistory() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    NotificationScreen(
                        uiState = NotificationUiState.init.copy(notificationHistories = listOf(NotificationHistoryUiModel(RecordType.DAILY,"1일 전",true))),
                        uiEvent = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("1일 전")
            .assertIsDisplayed()
    }

    @Test
    fun givenHasNoGoal_whenScreening_shownBanner() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    NotificationScreen(
                        uiState = NotificationUiState.init.copy(hasNoGoal = true),
                        uiEvent = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.current_goal_banner_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.current_goal_banner_body))
            .assertIsDisplayed()
    }
}

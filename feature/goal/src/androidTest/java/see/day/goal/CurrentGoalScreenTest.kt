package see.day.goal

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.screen.CurrentGoalScreen
import see.day.goal.state.CurrentGoalUiState
import see.day.goal.util.completeText
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType

class CurrentGoalScreenTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_when_shownTopBarAndTitle() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    CurrentGoalScreen(
                        uiState = CurrentGoalUiState.init,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.goal_acheive_title))
    }

    @Test
    fun givenGoalStage_when_shownGoalDescriptionAndTree() {
        val uiState = CurrentGoalUiState.init.copy(treeStage = TreeStage.STAGE_1)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    CurrentGoalScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(uiState.treeStage.completeText()))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(uiState.treeStage.name)
            .assertIsDisplayed()
    }

    @Test
    fun givenMainRecordTypeAndRecordDuring_when_shownMainRecordTypeAndRecordDuring() {
        val uiState = CurrentGoalUiState.init.copy(recordType = RecordType.DAILY, startDate = "2025-11-10", endDate = "2025-11-20")
        composeTestRule
            .setContent {
                SeeDayTheme {
                    CurrentGoalScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.my_goal_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(uiState.recordType.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.goal_during))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(uiState.startDate.replace("-",".").substring(2) + " ~ " + uiState.endDate.replace("-",".").substring(2))
            .assertIsDisplayed()
    }

    @Test
    fun given_when_shownAchieves() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    CurrentGoalScreen(
                        uiState = CurrentGoalUiState.init,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.achievement_rate_text))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.complete_days_text))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.day_streak_text))
            .assertIsDisplayed()
    }

    @Test
    fun given_when_shownResetGoalBanner() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    CurrentGoalScreen(
                        uiState = CurrentGoalUiState.init,
                        onAction = {}
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

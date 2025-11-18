package see.day.goal

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.goal.screen.ResetGoalScreen
import see.day.goal.state.reset.GoalResetStep
import see.day.goal.state.reset.ResetGoalUiState
import see.day.model.record.RecordType

class ResetGoalScreenTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }


    @Test
    fun givenRecordSelect_when_shownRecordTitle() {
        composeTestRule.setContent {
            SeeDayTheme {
                ResetGoalScreen(
                    uiState = ResetGoalUiState.init,
                    onAction = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithContentDescription("목표 재설정 ${GoalResetStep.RECORD.ordinal} 번째 아이콘")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.reset_goal_record_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("다음")
            .assertIsNotEnabled()

        RecordType.entries.forEach { recordType ->
            composeTestRule
                .onNodeWithText(recordType.title)
                .assertIsDisplayed()
        }
    }

    @Test
    fun givenGoalDaysSelect_when_shownGoalDaysTitle() {
        composeTestRule.setContent {
            SeeDayTheme {
                ResetGoalScreen(
                    uiState = ResetGoalUiState.init.copy(step = GoalResetStep.DAY),
                    onAction = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("목표 재설정 ${GoalResetStep.DAY.ordinal} 번째 아이콘")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.reset_goal_days_message))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("완료하기")
            .assertIsDisplayed()
            .assertIsNotEnabled()

        listOf("10일","20일","30일").forEach { day ->
            composeTestRule
                .onNodeWithText(day)
                .assertIsDisplayed()
        }
    }
}

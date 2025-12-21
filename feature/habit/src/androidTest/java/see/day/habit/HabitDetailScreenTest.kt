package see.day.habit

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.habit.screen.HabitDetailScreen
import see.day.habit.state.HabitDetailUiState
import see.day.model.record.RecordType
import see.day.model.record.habit.HabitRecordUiModel
import see.day.model.record.habit.HabitType

class HabitDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownHabit() {
        val uiState = HabitDetailUiState.init.copy(habitType = HabitType.EXERCISE)

        composeTestRule
            .setContent {
                SeeDayTheme {
                    HabitDetailScreen(
                        uiState = uiState,
                        onAction = {},
                        onClickBackButton = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(RecordType.HABIT.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(uiState.habitType.displayName)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("알림")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.write_record_text))
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun givenEditMode_whenScreening_shownHabit() {
        val uiState = HabitDetailUiState.init.copy(editMode = HabitDetailUiState.EditMode.Edit(originalRecord = HabitRecordUiModel("", habitType = HabitType.EXERCISE, false, 0, 0, "", false, true), recordId = ""))

        composeTestRule
            .setContent {
                SeeDayTheme {
                    HabitDetailScreen(
                        uiState = uiState,
                        onAction = {},
                        onClickBackButton = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithContentDescription("삭제 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.modifiy_record_text))
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("삭제 버튼")
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.record_delete_title))
            .assertIsDisplayed()


        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.record_delete_body))
            .assertIsDisplayed()
    }

    @Test
    fun givenSubHabit_whenScreening_shownMainSetHabit() {
        val uiState = HabitDetailUiState.init.copy(canBeMain = true)

        composeTestRule
            .setContent {
                SeeDayTheme {
                    HabitDetailScreen(
                        uiState = uiState,
                        onAction = {},
                        onClickBackButton = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_main))
            .assertIsDisplayed()

    }
}

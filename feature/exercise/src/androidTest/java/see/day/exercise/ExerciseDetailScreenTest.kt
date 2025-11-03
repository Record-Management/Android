package see.day.exercise

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.exercise.screen.ExerciseDetailScreen
import see.day.exercise.state.ExerciseDetailUiEvent
import see.day.exercise.state.ExerciseDetailUiState
import see.day.model.record.exercise.ExerciseRecordInput
import see.day.model.record.exercise.ExerciseType
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

class ExerciseDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownExercise() {
        val uiState = ExerciseDetailUiState.init

        composeTestRule
            .setContent {
                SeeDayTheme {
                    ExerciseDetailScreen(
                        uiState = uiState,
                        uiEvent = {},
                        onClickBackButton = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText(uiState.exerciseType.displayName)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(uiState.exerciseType.displayName)
            .assertIsDisplayed()

        val nodes = composeTestRule
            .onAllNodesWithText("운동 기록")

        nodes[0].assertIsDisplayed()

        nodes[1].assertIsDisplayed()

        composeTestRule
            .onNodeWithText("(1개 이상 필수 입력)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.write_record_text))
            .performScrollTo()
            .assertIsDisplayed()
    }

    @Test
    fun givenEditMode_whenScreening_shownEditScreen() {
        val uiState = ExerciseDetailUiState.init.copy(editMode = ExerciseDetailUiState.EditMode.Edit(originalRecord = ExerciseRecordInput(ExerciseType.GOLF, "", KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), "", "", "", "", listOf()), ""))

        composeTestRule.setContent {
            SeeDayTheme {
                ExerciseDetailScreen(
                    uiState = uiState,
                    uiEvent = {},
                    onClickBackButton = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("삭제 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.modifiy_record_text))
            .performScrollTo()
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun given_whenExerciseChanged_shownExerciseChanged() {
        val originExerciseType = ExerciseType.GOLF
        val changedExerciseType = ExerciseType.RUNNING
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(ExerciseDetailUiState.init.copy(exerciseType = originExerciseType)) }
            SeeDayTheme {
                ExerciseDetailScreen(
                    uiState = uiState,
                    uiEvent = { event ->
                        when (event) {
                            is ExerciseDetailUiEvent.OnExerciseTypeChanged -> {
                                uiState = uiState.copy(exerciseType = event.exerciseType)
                            }

                            else -> {}
                        }
                    },
                    onClickBackButton = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(originExerciseType.displayName)
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText("운동 재선택")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(changedExerciseType.displayName)
            .performScrollTo()
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(changedExerciseType.displayName)
            .assertIsDisplayed()
    }
}

package see.day.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.home.screen.HomeScreen
import see.day.home.state.HomeUiState
import see.day.model.goal.TreeStage
import see.day.model.record.RecordType

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenRecordTypeAndGoalDays_when_shownRecordTypeAndGoalDays() {
        val uiState = HomeUiState.init.copy(mainRecordType = RecordType.DAILY, goalDays = 10)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    HomeScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("D-${uiState.goalDays}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(uiState.mainRecordType?.title ?: throw IllegalStateException())
            .assertIsDisplayed()

    }

    @Test
    fun givenTreeStage_when_shownMainImage() {
        val uiState = HomeUiState.init.copy(treeStage = TreeStage.STAGE_1)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    HomeScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithContentDescription(uiState.treeStage?.name ?: throw IllegalStateException())
            .assertIsDisplayed()
    }

    @Test
    fun givenMainRecordNull_when_shownNothing() {
        val uiState = HomeUiState.init.copy(mainRecordType = null, goalDays = null)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    HomeScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("D-${uiState.goalDays}")
            .assertIsNotDisplayed()

        composeTestRule
            .onNodeWithContentDescription(uiState.mainRecordType?.title ?: "목표 미설정")
            .assertIsNotDisplayed()
    }

    @Test
    fun givenCurrentDateAndFilterType_when_shownCurrentDateAndFilterType() {
        val uiState = HomeUiState.init.copy(currentYear = 2025, currentMonth = 12, mainRecordType = null)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    HomeScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("${uiState.currentYear}.${uiState.currentMonth}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("기록 타입 필터")
            .assertIsDisplayed()
    }

    @Test
    fun given_whenClickSelectedDate_shownDateSelectSpinner() {
        val uiState = HomeUiState.init.copy(currentYear = 2026, selectedMonth = 1, selectedDay = 1, mainRecordType = null)
        composeTestRule
            .setContent {
                SeeDayTheme {
                    HomeScreen(
                        uiState = uiState,
                        onAction = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("${uiState.currentYear}.${uiState.currentMonth}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("${uiState.currentYear}.${uiState.currentMonth}")
            .performClick()

        composeTestRule
            .onNodeWithText("오늘")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("완료")
            .assertIsDisplayed()
    }
}

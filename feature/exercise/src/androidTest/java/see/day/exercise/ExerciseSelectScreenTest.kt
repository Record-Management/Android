package see.day.exercise

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.designsystem.theme.SeeDayTheme
import see.day.exercise.screen.ExerciseSelectScreen

class ExerciseSelectScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownDefault() {
        composeTestRule
            .setContent {
                SeeDayTheme {
                    ExerciseSelectScreen(
                        onClickChangeRecordType = {type, boolean -> },
                        onBack = {},
                        onClickExerciseType = {}
                    )
                }
            }

        composeTestRule
            .onNodeWithText("운동 선택")
            .assertIsDisplayed()
    }
}

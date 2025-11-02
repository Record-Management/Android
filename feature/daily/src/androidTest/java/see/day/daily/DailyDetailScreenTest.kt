package see.day.daily

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.daily.screen.DailyDetailScreen
import see.day.daily.state.DailyDetailUiEvent
import see.day.daily.state.DailyDetailUiState
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType

class DailyDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenScreening_shownTitleAndEmotion() {
        val uiState = DailyDetailUiState.init

        composeTestRule.setContent {
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    uiEvent = {},
                    onClickBackButton = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(RecordType.DAILY.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("emotion ${uiState.emotion.name}")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(uiState.dateTime.formatFullDate())
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(uiState.dateTime.formatFullTime())
            .assertIsDisplayed()
    }

    @Test
    fun given_whenTextChanged_shownText() {
        val changedText = "안녕하세요 오늘의 기록입니다."
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(DailyDetailUiState.init) }
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    uiEvent = { event ->
                        when(event) {
                            is DailyDetailUiEvent.OnChangedText -> {
                                uiState = uiState.copy(text = event.text)
                            }
                            else -> {

                            }
                        }
                    },
                    onClickBackButton = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_daily_place_holder))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_daily_place_holder))
            .performTextInput(changedText)

        composeTestRule
            .onNodeWithText(changedText)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_daily_place_holder))
            .assertIsNotDisplayed()
    }

    @Test
    fun given_whenTextChanged_shownEnabledCompleteButton() {
        val changedText = "안녕하세요 오늘의 기록입니다."
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(DailyDetailUiState.init) }
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    uiEvent = { event ->
                        when(event) {
                            is DailyDetailUiEvent.OnChangedText -> {
                                uiState = uiState.copy(text = event.text)
                            }
                            else -> {

                            }
                        }
                    },
                    onClickBackButton = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.write_record_text))
            .assertIsNotEnabled()

        composeTestRule
            .onNodeWithText(context.getString(R.string.record_daily_place_holder))
            .performTextInput(changedText)

        composeTestRule
            .onNodeWithText(changedText)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.write_record_text))
            .assertIsEnabled()

    }
}

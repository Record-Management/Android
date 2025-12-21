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
import see.day.model.record.daily.DailyEmotion
import see.day.model.record.daily.DailyRecordInput
import see.day.model.time.DateTime
import see.day.model.time.formatter.KoreanDateTimeFormatter

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
        val uiState = DailyDetailUiState.init.copy(editMode = DailyDetailUiState.EditMode.Create)

        composeTestRule.setContent {
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    onAction = {},
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

        composeTestRule
            .onNodeWithContentDescription("뒤로가기 버튼")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.write_record_text))
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun givenEditMode_whenScreening_shownBackButtonAndDeleteButton() {
        val uiState = DailyDetailUiState.init.copy(editMode = DailyDetailUiState.EditMode.Edit(originalRecord = DailyRecordInput("",DailyEmotion.Love,KoreanDateTimeFormatter(DateTime.now(DateTime.korea)), listOf()),""))

        composeTestRule.setContent {
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    onAction = {},
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
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun given_whenTextChanged_shownText() {
        val changedText = "안녕하세요 오늘의 기록입니다."
        composeTestRule.setContent {
            var uiState by remember { mutableStateOf(DailyDetailUiState.init) }
            SeeDayTheme {
                DailyDetailScreen(
                    uiState = uiState,
                    onAction = { event ->
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
                    onAction = { event ->
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

    @Test
    fun givenDailyText_whenClickBackButton_shownBackDialog() {

    }
}

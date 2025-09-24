package see.day.daily

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import see.day.daily.screen.DailyScreen
import see.day.model.record.RecordType

class DailyScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_whenClickRecordTypeChange_shownOpenDialog() {
        composeTestRule.setContent {
            DailyScreen(
                onChangedRecordType = { type, clearBackStack -> },
                onClickBackButton = {},
                onClickEmotion = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.select_daily_emotion))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_record_type))
            .performClick()


        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.change_record_title))
            .assertIsDisplayed()
    }

    @Test
    fun given_whenClickDifferentType_shownChangeButtonEnabled() {
        val currentType = RecordType.DAILY
        composeTestRule.setContent {
            DailyScreen(
                onChangedRecordType = { type, clearBackStack -> },
                onClickBackButton = {},
                onClickEmotion = {}
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.change_record_type))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(see.day.ui.R.string.change_record))
            .assertIsNotEnabled()

        RecordType.entries.forEach { type ->
            if(currentType != type) {
                composeTestRule
                    .onNodeWithText(type.title)
                    .performClick()

                composeTestRule
                    .onNodeWithText(context.getString(see.day.ui.R.string.change_record))
                    .assertIsEnabled()
            }

        }
    }
}

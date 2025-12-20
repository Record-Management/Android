package record.daily.login

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import record.daily.login.component.Permissions
import record.daily.login.screen.PermissionScreen
import see.day.designsystem.theme.SeeDayTheme

class PermissionScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = composeTestRule.activity.baseContext
    }

    @Test
    fun given_when_shownPermissionTitle() {
        // given & when
        composeTestRule
            .setContent {
                SeeDayTheme {
                    PermissionScreen(onAction = {})
                }
            }

        // shown

        composeTestRule
            .onNodeWithText(context.getString(R.string.permission_description_title))
            .assertIsDisplayed()

        Permissions.entries.forEach { permission ->
            composeTestRule
                .onNodeWithText(context.getString(permission.title) + if (!permission.isRequired) " " + context.getString(R.string.optional) else "")
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithText(context.getString(permission.body))
                .assertIsDisplayed()
        }


    }
}

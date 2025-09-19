package see.day.onboarding.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.onboarding.R

@Composable
fun OnboardingCompleteLabel(modifier: Modifier = Modifier, @StringRes labelText: Int) {
    Surface(
        modifier = modifier.padding(top = 14.dp).padding(horizontal = 16.dp).fillMaxWidth().background(gray20),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 13.dp, bottom = 14.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(see.day.designsystem.R.drawable.ic_checked),
                contentDescription = "체크",
                modifier = Modifier.size(20.dp)
            )
            Text(
                modifier = Modifier.padding(start = 14.dp),
                text = stringResource(labelText),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingComponentLabelPreview() {
    SeeDayTheme {
        OnboardingCompleteLabel(
            labelText = R.string.onboard_complete_label_1
        )
    }
}

package record.daily.login.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import record.daily.login.R
import record.daily.login.component.PermissionCard
import record.daily.login.component.Permissions
import record.daily.login.state.permission.PermissionUiEffect
import record.daily.login.state.permission.PermissionUiEvent
import record.daily.login.viewmodel.PermissionViewModel
import see.day.designsystem.theme.SeeDayTheme
import see.day.ui.button.CompleteButton

@Composable
internal fun PermissionScreenRoot(
    viewModel: PermissionViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    BackHandler(true) {
        (context as? Activity)?.finish()
    }
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                PermissionUiEffect.OnGoLogin -> {
                    onBack()
                }
            }
        }
    }

    PermissionScreen(
        onAction = viewModel::onAction
    )
}

@Composable
internal fun PermissionScreen(
    modifier: Modifier = Modifier,
    onAction: (PermissionUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 66.dp, start = 16.dp, end = 16.dp)
            .systemBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.permission_description_title),
            style = MaterialTheme.typography.bodyLarge
        )
        Column(
            modifier = Modifier.padding(top = 60.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Permissions.entries.forEach { permission ->
                PermissionCard(
                    permission = permission
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        CompleteButton(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.confirm_permission),
            isEnabled = true,
            onClick = {
                onAction(PermissionUiEvent.OnClickPermissionConfirm)
            }
        )
    }
}

@Preview
@Composable
internal fun PermissionScreenPreview() {
    SeeDayTheme {
        PermissionScreen(
            onAction = {}
        )
    }
}

package see.day.schedule.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.Typography
import see.day.designsystem.theme.gray100
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.primaryColor
import see.day.model.schedule.SchedulePaletteColor
import see.day.schedule.R
import see.day.util.toColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ColorPaletteBottomSheet(
    modifier: Modifier = Modifier,
    selectedColor: SchedulePaletteColor,
    onDismiss: () -> Unit,
    onColorSelected: (SchedulePaletteColor) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var currentColor by remember { mutableStateOf(selectedColor) }
    val colors = SchedulePaletteColor.entries.toList()
    val dismissBottomSheet: (isChanged: Boolean) -> Unit = { shouldApplyChange ->
        coroutineScope.launch {
            sheetState.hide()
            if (shouldApplyChange) {
                onColorSelected(currentColor)
            }
            onDismiss()
        }
    }
    Column(
        modifier = modifier
    ) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { dismissBottomSheet(false) },
            dragHandle = {},
            containerColor = Color.White,
            sheetGesturesEnabled = false,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(see.day.ui.R.drawable.ic_arrow_left),
                        contentDescription = "뒤로 가기 버튼",
                        tint = gray100,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { dismissBottomSheet(false) },
                    )

                    Text(
                        stringResource(R.string.color_set),
                        style = Typography.titleSmall,
                    )
                    Text(
                        modifier = Modifier.clickable {
                            dismissBottomSheet(true)
                        },
                        text = stringResource(R.string.complete),
                        style = Typography.titleSmall
                    )
                }
                Column(
                    modifier = Modifier
                        .background(color = gray20)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.color),
                            style = Typography.titleSmall,
                        )
                        Column(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            colors.chunked(5).forEach { rowColors ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    rowColors.forEach { paletteColor ->
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    color = paletteColor.toColor(),
                                                    shape = CircleShape
                                                )
                                                .clickable { currentColor = paletteColor },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (currentColor == paletteColor) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_checked),
                                                    contentDescription = "체크 아이콘",
                                                    tint = Color.White,
                                                )
                                            }
                                        }
                                    }
                                    repeat(5 - rowColors.size) {
                                        Box(modifier = Modifier.size(40.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ColorPaletteBottomSheetPreview() {
    var selectedColor by remember { mutableStateOf(SchedulePaletteColor.ORANGE) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    SeeDayTheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = { isBottomSheetOpen = true }
            ) {
                Text(text = "색상 설정 바텀시트")
            }
            Text(
                text = "색상 설정",
                style = Typography.displayMedium,
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(selectedColor.toColor(), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
            }
        }
        if (isBottomSheetOpen) {
            ColorPaletteBottomSheet(
                selectedColor = selectedColor,
                onDismiss = { isBottomSheetOpen = false },
                onColorSelected = {
                    selectedColor = it
                },
            )
        }
    }
}

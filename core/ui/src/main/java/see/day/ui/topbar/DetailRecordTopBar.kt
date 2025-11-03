package see.day.ui.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.model.record.RecordType
import see.day.ui.R

@Composable
fun DetailRecordTopBar(
    modifier: Modifier = Modifier,
    recordType: RecordType,
    editMode: EditMode,
    onClickCloseButton: () -> Unit,
    onClickDeleteButton: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        when(editMode) {
            EditMode.ADD -> { }
            EditMode.UPDATE -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable { onClickCloseButton() },
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "뒤로가기 버튼",
                )
            }
        }
        Text(
            text = recordType.title,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )

        when(editMode) {
            EditMode.ADD -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .clickable { onClickCloseButton() },
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "뒤로가기 버튼"
                )
            }
            EditMode.UPDATE -> {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .clickable { onClickDeleteButton() },
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = "삭제 버튼",
                )
            }
        }


    }
}

enum class EditMode {
    ADD, UPDATE
}

@Preview
@Composable
private fun DetailRecordAddModeTopBarPreview() {
    SeeDayTheme {
        DetailRecordTopBar(
            recordType = RecordType.DAILY,
            editMode = EditMode.ADD,
            onClickCloseButton = {},
            onClickDeleteButton = {}
        )
    }
}

@Preview
@Composable
private fun DetailRecordUpdateModeTopBarPreview() {
    SeeDayTheme {
        DetailRecordTopBar(
            recordType = RecordType.DAILY,
            editMode = EditMode.UPDATE,
            onClickCloseButton = {},
            onClickDeleteButton = {}
        )
    }
}

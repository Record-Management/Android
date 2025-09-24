package see.day.ui.photo

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import see.day.designsystem.theme.SeeDayTheme

@Composable
fun RecordDetailPhotoRow(
    modifier: Modifier = Modifier,
    context: Context,
    uris: List<Uri>,
    onRemovePhotos: (Uri) -> Unit,
    onClickAddPhotos: (List<Uri>) -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        uris.forEach { uri ->
            PhotoComponent(
                uri = uri.toString(),
                isRemovable = true,
                onClickDeleteButton = {
                    onRemovePhotos(it.toUri())
                }
            )
        }
        if (uris.size < 3) {
            AddPhotoButton(
                modifier = modifier.padding(top = 6.dp),
                context = context,
                currentPhotoCount = uris.size,
                onClickPhotoAddButton = onClickAddPhotos
            )
        }
    }
}

@Preview
@Composable
private fun RecordDetailPhotoRowPreview() {
    val context = LocalContext.current
    SeeDayTheme {
        RecordDetailPhotoRow(
            context = context,
            uris = listOf(),
            onRemovePhotos = {},
            onClickAddPhotos = {}
        )
    }
}

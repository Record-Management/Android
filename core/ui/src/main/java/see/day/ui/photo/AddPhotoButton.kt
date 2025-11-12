package see.day.ui.photo

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import see.day.designsystem.theme.SeeDayTheme
import see.day.designsystem.theme.gray20
import see.day.designsystem.theme.gray50
import see.day.ui.R
import see.day.util.permission.hasPhotoPermissions
import see.day.util.permission.isPhotoPickerSupported
import see.day.util.permission.openPhotoSettings

@Composable
fun AddPhotoButton(modifier: Modifier = Modifier, context: Context, currentPhotoCount: Int, onClickPhotoAddButton: (List<String>) -> Unit) {
    var hasPermissionsPreApi33 by remember { mutableStateOf(hasPhotoPermissions(context)) }

    val pickMediaLauncher = if (currentPhotoCount == 2) {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                onClickPhotoAddButton(listOf(uri.toString()))
            }
        }
    } else {
        rememberLauncherForActivityResult(
            contract = PickMultipleVisualMedia(maxItems = 3 - currentPhotoCount)
        ) { selectedUris ->
            if (selectedUris.isNotEmpty()) {
                onClickPhotoAddButton(selectedUris.map { it.toString() })
            }
        }
    }

    val photoPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            hasPermissionsPreApi33 = true
        } else {
            openPhotoSettings(context)
        }
    }

    Column(
        modifier = modifier
            .size(100.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(gray20)
            .clickable {
                if (isPhotoPickerSupported() || hasPermissionsPreApi33) {
                    pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    photoPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            .padding(vertical = 23.dp, horizontal = 17.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.camera),
            contentDescription = "이미지 추가 버튼",
            modifier = modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 6.dp)
                .size(30.dp),
            tint = gray50
        )
        Text(
            text = "+ 사진 올리기",
            style = MaterialTheme.typography.headlineLarge,
            color = gray50
        )
    }
}

@Preview
@Composable
private fun AddPhotoButtonPreview() {
    var photoCount by remember { mutableStateOf(0) }
    val context = LocalContext.current
    if (photoCount >= 3) {
        return
    }
    SeeDayTheme {
        AddPhotoButton(
            context = context,
            currentPhotoCount = photoCount,
            onClickPhotoAddButton = { uris ->
                photoCount = uris.size
                Toast.makeText(context, uris.toString(), Toast.LENGTH_SHORT).show()
            }
        )
    }
}

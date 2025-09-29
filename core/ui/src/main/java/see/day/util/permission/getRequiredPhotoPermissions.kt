package see.day.util.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

fun requiredPhotoPermissionsPreApi33(): String  {
    return Manifest.permission.READ_EXTERNAL_STORAGE
}

// API 32 이하에서 권한을 요청하는 함수 작성

// 권한을 요청하고 사진칸으로 이동하는 함수 작성
// 백스택에서도 API 32 이하에서는 bundle에 Uri들이 있는지 조회해야함

fun hasPhotoPermissions(context: Context): Boolean {
    return requiredPhotoPermissionsPreApi33().let { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

fun isPhotoPickerSupported(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}

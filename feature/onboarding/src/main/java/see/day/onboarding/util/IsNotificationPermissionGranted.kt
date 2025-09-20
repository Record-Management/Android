package see.day.onboarding.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun isNotificationPermissionGranted(context: Context): Boolean {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        // Android 12 이하에서는 알림 권한이 자동 허용
        true
    }
}

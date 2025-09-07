package see.day.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = primaryColor,
        onPrimary = onPrimary,
        background = backgroundColor,
        onBackground = onBackground,
        onPrimaryContainer = Color.White,
        primaryContainer = Color.White,
        surface = surface,
        onSurface = onSurface,
        surfaceContainer = Color.White,
        error = Color.White,
        onError = onError,
        errorContainer = Color.White
    )

private val LightColorScheme =
    lightColorScheme(
        primary = primaryColor,
        onPrimary = onPrimary,
        background = backgroundColor,
        onBackground = onBackground,
        surfaceContainer = Color.White,
        onPrimaryContainer = Color.White,
        primaryContainer = Color.White,
        surface = surface,
        onSurface = onSurface,
        error = Color.White,
        onError = onError,
        errorContainer = Color.White
    )

@Composable
fun SeeDayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package see.day.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
        errorContainer = Color.White,
        onSecondary = onSecondary
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
        errorContainer = Color.White,
        onSecondary = onSecondary
    )

@Composable
fun SeeDayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

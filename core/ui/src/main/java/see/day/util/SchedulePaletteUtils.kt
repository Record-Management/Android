package see.day.util

import androidx.compose.ui.graphics.Color
import see.day.model.schedule.SchedulePaletteColor

fun SchedulePaletteColor.toColor(): Color {
    return when (this) {
        SchedulePaletteColor.RED -> Color(0xFFFF3635)
        SchedulePaletteColor.ORANGE -> Color(0xFFFF9528)
        SchedulePaletteColor.YELLOW -> Color(0xFFFFFC3A)
        SchedulePaletteColor.GREEN -> Color(0xFF26D552)
        SchedulePaletteColor.BLUE -> Color(0xFF2D69E1)
        SchedulePaletteColor.INDIGO -> Color(0xFF362C9B)
        SchedulePaletteColor.PINK -> Color(0xFFF304F7)
        SchedulePaletteColor.GRAY -> Color(0xFFE9E9E9)
    }
}

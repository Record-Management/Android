package see.day.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import see.day.designsystem.R

val preTendFont =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal)
    )

val Typography =
    Typography(
        // P/22/Bold
        bodyLarge =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W700,
                fontSize = 22.sp,
                lineHeight = 22.sp * 1.5f,
                color = gray100
            ),
        // P/20/Bold
        bodyMedium =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
                lineHeight = 20.sp * 1.5f,
                color = gray100
            ),
        // P/20/SemiBold
        bodySmall =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                lineHeight = 20.sp * 1.5f,
                color = gray100
            ),
        // P/18/SemiBold
        titleLarge =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                lineHeight = 18.sp * 1.5f,
                color = gray100
            ),
        // P/18/Medium
        titleMedium =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp,
                lineHeight = 18.sp * 1.5f,
                color = gray100
            ),
        // P/16/SemiBold
        titleSmall =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                lineHeight = 16.sp * 1.5f,
                color = gray100
            ),
        // P/16/Medium
        displayLarge = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 16.sp * 1.5f,
            color = gray100
        ),
        // P/16/Regular
        displayMedium =
            TextStyle(
                fontFamily = preTendFont,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                lineHeight = 16.sp * 1.5f,
                color = gray100
            ),
        // P/14/Semibold
        displaySmall = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            color = gray100
        ),
        // P/14/Medium
        labelMedium = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            color = gray100
        ),
        // P/14/Regular
        labelSmall = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            lineHeight = 14.sp * 1.5f,
            color = gray100
        ),
        // P/12/Medium
        headlineLarge = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 12.sp * 1.5f,
            color = gray100
        ),
        // P/12/Regular
        headlineMedium = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 12.sp * 1.5f,
            color = gray100
        ),
        // P/10/Medium
        headlineSmall = TextStyle(
            fontFamily = preTendFont,
            fontWeight = FontWeight.W500,
            fontSize = 10.sp,
            lineHeight = 10.sp * 1.0f,
            color = gray100
        )
    )

@Preview
@Composable
fun TypoPreview() {
    val string = "개미가 줄을 타고 올라갑니다."

    Column {
        Text(
            text = string,
            style = Typography.bodyLarge
        )

        Text(
            text = string,
            style = Typography.headlineSmall
        )
    }

}

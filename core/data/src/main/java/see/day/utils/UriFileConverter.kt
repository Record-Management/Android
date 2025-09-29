package see.day.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class UriFileConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun convert(uri: String): File {
        val contentResolver = context.contentResolver

        // 임시 파일 생성
        val file = File.createTempFile("picked_", ".jpg", context.cacheDir)

        contentResolver.openInputStream(uri.toUri())?.use { inputStream ->
            // Bitmap 생성
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // 자동 리사이즈
            val resizedBitmap = resizeBitmapAuto(originalBitmap)

            // JPEG 압축 저장
            FileOutputStream(file).use { out ->
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }

            // Bitmap 메모리 해제
            originalBitmap.recycle()
            resizedBitmap.recycle()
        }

        return file
    }

    private fun getResizeDimension(width: Int, height: Int): Int {
        val maxDimension = maxOf(width, height)

        return when {
            maxDimension > 2000 -> 600
            maxDimension > 1000 -> 400
            maxDimension > 500 -> 300
            else -> maxDimension // 이미 작은 경우 그대로 유지
        }
    }

    private fun resizeBitmapAuto(bitmap: Bitmap): Bitmap {
        val targetSize = getResizeDimension(bitmap.width, bitmap.height)

        val scale = targetSize.toFloat() / maxOf(bitmap.width, bitmap.height)
        val newWidth = (bitmap.width * scale).toInt()
        val newHeight = (bitmap.height * scale).toInt()

        return bitmap.scale(newWidth, newHeight)
    }
}

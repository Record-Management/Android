package see.day.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import see.day.domain.repository.PhotoRepository
import see.day.model.exception.NoDataException
import see.day.network.PhotoService
import see.day.utils.UriFileConverter
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val uriFileConverter: UriFileConverter,
    private val photoService: PhotoService
) : PhotoRepository {

    override suspend fun insertPhotos(uris: List<String>): Result<List<String>> {
        return runCatching {
            val photoFiles = uris.map { uri ->
                uriFileConverter.convert(uri)
            }


            val request = photoFiles.map { file ->
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("files", file.name, requestFile)
            }
            photoService.uploadPhotos(request).data?.fileUrls ?: throw NoDataException()
        }
    }
}

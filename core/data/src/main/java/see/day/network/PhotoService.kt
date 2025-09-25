package see.day.network

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import see.day.network.dto.CommonResponse
import see.day.network.dto.photo.PhotoResponse

interface PhotoService {

    @Multipart
    @POST("/api/files/upload")
    suspend fun uploadPhotos(
        @Part files: List<MultipartBody.Part>
    ) : CommonResponse<PhotoResponse>
}

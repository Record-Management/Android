package see.day.domain.repository

interface PhotoRepository {

    suspend fun insertPhotos(uris: List<String>) : Result<List<String>>
}

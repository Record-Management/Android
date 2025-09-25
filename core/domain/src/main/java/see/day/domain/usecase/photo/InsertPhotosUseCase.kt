package see.day.domain.usecase.photo

import see.day.domain.repository.PhotoRepository
import javax.inject.Inject

class InsertPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
){

    suspend operator fun invoke(uris: List<String>) : Result<List<String>> {
        return photoRepository.insertPhotos(uris)
    }
}

package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import see.day.model.record.RecordType
import javax.inject.Inject

class GetUserRecordTypeUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke() : Result<RecordType> {
        return userRepository.getMainRecordType()
    }
}

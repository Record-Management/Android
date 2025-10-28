package see.day.domain.usecase.user

import see.day.domain.repository.UserRepository
import see.day.model.record.RecordType
import javax.inject.Inject

class GetMainRecordTypeUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke() : RecordType? {
        return userRepository.getUser().getOrNull()?.mainRecordType
    }
}

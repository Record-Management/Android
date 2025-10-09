package see.day.network

import retrofit2.http.Body
import retrofit2.http.POST
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.ExerciseRecordResponse
import see.day.network.dto.record.exercise.ExerciseRecordInputRequest

interface ExerciseRecordService {

    @POST("api/exercise-records")
    suspend fun postExerciseRecord(@Body exerciseRecordInput: ExerciseRecordInputRequest) : CommonResponse<ExerciseRecordResponse>
}

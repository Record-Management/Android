package see.day.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import see.day.network.dto.CommonResponse
import see.day.network.dto.record.ExerciseRecordResponse
import see.day.network.dto.record.exercise.ExerciseRecordEditRequest
import see.day.network.dto.record.exercise.ExerciseRecordInputRequest

interface ExerciseRecordService {

    @POST("api/exercise-records")
    suspend fun postExerciseRecord(@Body exerciseRecordInput: ExerciseRecordInputRequest) : CommonResponse<ExerciseRecordResponse>

    @PUT("api/exercise-records/{exerciseRecordId}")
    suspend fun updateExerciseRecord(
        @Path("exerciseRecordId") recordId: String,
        @Body exerciseRecordEdit: ExerciseRecordEditRequest
    ) : CommonResponse<ExerciseRecordResponse>
}

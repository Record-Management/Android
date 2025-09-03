package see.day.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import record.daily.model.exception.BadRequestException
import record.daily.model.exception.InvalidValueException
import record.daily.model.exception.NetworkErrorException
import record.daily.model.exception.NotFoundException
import record.daily.model.exception.UnAuthorizedException
import retrofit2.HttpException
import see.day.network.dto.CommonResponse
import timber.log.Timber

object ErrorUtils {

    inline fun <reified T, R> T.createResult(call: T.() -> R): Result<R> {
        return try {
            Result.success(call())
        } catch (e: HttpException) {
            Timber.e(e.message)
            Result.failure(mapToCustomException<T>(e))
        } catch (e: Exception) {
            Timber.e(e.message)
            Result.failure(NetworkErrorException(e.message))
        }
    }

    inline fun <reified T> mapToCustomException(it: HttpException): Exception {
        val error = it.response()?.errorBody()?.string() ?: throw InvalidValueException(it.message)
        val response = Json.decodeFromString<CommonResponse<JsonElement?>>(error)
        return when (it.code()) {
            401 -> UnAuthorizedException(response.message)
            404 -> NotFoundException(response.message)
            400 -> BadRequestException(response.message)
            500 -> InvalidValueException(response.message)
            else -> IllegalStateException(response.message)
        }
    }
}

package see.day.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import retrofit2.HttpException
import see.day.model.exception.BadRequestException
import see.day.model.exception.InvalidValueException
import see.day.model.exception.NetworkErrorException
import see.day.model.exception.NotFoundException
import see.day.model.exception.UnAuthorizedException
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
            500 -> NetworkErrorException(response.message)
            else -> IllegalStateException(response.message)
        }
    }
}

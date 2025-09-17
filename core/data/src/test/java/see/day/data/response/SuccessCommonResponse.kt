package see.day.data.response

import see.day.network.dto.CommonResponse

fun <T> successCommonResponse(message: String = "성공적으로 데이터를 불러왔습니다.", response: T): CommonResponse<T> {
    return CommonResponse(
        code = "S200",
        statusCode = 200,
        message = message,
        data = response
    )
}

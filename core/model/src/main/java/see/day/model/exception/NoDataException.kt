package see.day.model.exception

data class NoDataException(override val message: String? = "데이터가 존재하지 않습니다.") : RuntimeException()

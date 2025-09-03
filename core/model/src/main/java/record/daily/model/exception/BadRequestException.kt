package record.daily.model.exception

data class BadRequestException(override val message: String?) : RuntimeException()

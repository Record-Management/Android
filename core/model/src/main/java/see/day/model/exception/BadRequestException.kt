package see.day.model.exception

data class BadRequestException(override val message: String?) : RuntimeException()

package record.daily.model.exception

data class NotFoundException(override val message: String?) : RuntimeException()

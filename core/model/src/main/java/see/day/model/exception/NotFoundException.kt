package see.day.model.exception

data class NotFoundException(override val message: String?) : RuntimeException()

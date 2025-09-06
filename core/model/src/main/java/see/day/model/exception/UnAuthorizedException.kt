package see.day.model.exception

data class UnAuthorizedException(override val message: String?) : RuntimeException()

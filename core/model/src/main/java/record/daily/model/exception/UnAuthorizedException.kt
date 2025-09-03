package record.daily.model.exception

data class UnAuthorizedException(override val message: String?) : RuntimeException()

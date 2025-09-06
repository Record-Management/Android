package see.day.model.exception

data class NetworkErrorException(override val message: String? = "NetworkErrorException") : RuntimeException()

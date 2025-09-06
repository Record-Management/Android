package see.day.model.exception

data class InvalidValueException(override val message: String? = "InvalidValueException") : RuntimeException()

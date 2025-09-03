package record.daily.model.exception

data class InvalidValueException(override val message: String? = "InvalidValueException") : RuntimeException()

package ai.vektor.ktor.binder.converters

import java.util.UUID

class LongConverter : ParamConverter<Long> {
    override fun convert(value: String): Long? = value.toLongOrNull()
}

class IntConverter : ParamConverter<Int> {
    override fun convert(value: String): Int? = value.toIntOrNull()
}

class DoubleConverter : ParamConverter<Double> {
    override fun convert(value: String): Double? = value.toDoubleOrNull()
}

class UUIDConverter : ParamConverter<UUID> {
    override fun convert(value: String): UUID? = try {
        UUID.fromString(value)
    } catch (e: IllegalArgumentException) {
        null
    }
}

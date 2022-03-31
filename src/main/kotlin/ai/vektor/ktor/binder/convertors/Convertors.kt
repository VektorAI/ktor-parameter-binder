package ai.vektor.ktor.binder.convertors

import java.util.UUID
import kotlin.reflect.full.createType

class LongConverter : ParameterConverter<Long> {
    override fun convert(value: String): Long? = value.toLongOrNull()
}

class IntConverter : ParameterConverter<Int> {
    override fun convert(value: String): Int? = value.toIntOrNull()
}

class DoubleConverter : ParameterConverter<Double> {
    override fun convert(value: String): Double? = value.toDoubleOrNull()
}

class UUIDConverter : ParameterConverter<UUID> {
    override fun convert(value: String): UUID? = try {
        UUID.fromString(value)
    } catch (e: IllegalArgumentException) {
        null
    }
}

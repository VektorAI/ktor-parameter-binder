package ai.vektor.ktor.binder.convertors

interface ParameterConverter<T> {
    fun convert(value: String): T?
}
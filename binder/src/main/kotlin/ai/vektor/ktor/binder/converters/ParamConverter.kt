package ai.vektor.ktor.binder.converters

interface ParamConverter<T> {
    fun convert(value: String): T?
}

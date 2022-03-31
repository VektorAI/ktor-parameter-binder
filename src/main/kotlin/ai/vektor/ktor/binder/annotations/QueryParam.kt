package ai.vektor.ktor.binder.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Param
annotation class QueryParam(val paramName: String)
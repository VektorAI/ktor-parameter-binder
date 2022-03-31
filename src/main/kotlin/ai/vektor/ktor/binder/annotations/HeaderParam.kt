package ai.vektor.ktor.binder.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Param
annotation class HeaderParam(val headerName: String)
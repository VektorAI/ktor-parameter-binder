package ai.vektor.ktor.binder.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class HeaderParam(val headerName: String)
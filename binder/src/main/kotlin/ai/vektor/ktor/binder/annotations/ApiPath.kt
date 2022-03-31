package ai.vektor.ktor.binder.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ApiPath(val path: String)

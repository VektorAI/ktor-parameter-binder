package ai.vektor.ktor.binder.annotations

enum class Method {
    GET,
    POST,
    DELETE,
    PATCH,
    PUT,
    OPTIONS,
    HEAD,
}

@Target(AnnotationTarget.FUNCTION)
annotation class ApiMethod(val method: Method)

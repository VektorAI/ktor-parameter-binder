package ai.vektor.ktor.binder.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Auth(vararg val configurations: String = [])

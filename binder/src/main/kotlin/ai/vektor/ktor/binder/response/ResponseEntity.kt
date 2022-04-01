package ai.vektor.ktor.binder.response

import io.ktor.http.HttpStatusCode

data class ResponseEntity(val status: HttpStatusCode, val value: Any? = null) {
    companion object {
        fun ok(value: Any? = null) = ResponseEntity(HttpStatusCode.OK, value)
        fun notFound(value: Any? = null) = ResponseEntity(HttpStatusCode.NotFound, value)
    }
}

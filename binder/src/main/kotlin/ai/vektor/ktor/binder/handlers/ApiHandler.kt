package ai.vektor.ktor.binder.handlers

import io.ktor.application.call
import io.ktor.http.HttpMethod
import io.ktor.response.respond
import io.ktor.routing.route
import io.ktor.routing.Route
import kotlin.reflect.KCallable
import kotlin.reflect.full.callSuspend

data class ApiHandler(
        val path: String,
        val method: HttpMethod,
        val handler: KCallable<Any>
)

fun Route.registerHandler(
        apiHandler: ApiHandler,
        binder: ParamBinder
): Route {
    val handler = apiHandler.handler
    return route(apiHandler.path, apiHandler.method) {
        handle {
            val args = handler.parameters.map {
                val value = binder.bind(it, this.call)
                if (value == null && !it.isOptional) {
                    throw IllegalStateException("Can't bind non-optional parameter ${it.name}")
                }
                value
            }

            val response = if (handler.isSuspend) {
                handler.callSuspend(*args.toTypedArray())
            } else {
                handler.call(*args.toTypedArray())
            }
            call.respond(response)
        }
    }
}

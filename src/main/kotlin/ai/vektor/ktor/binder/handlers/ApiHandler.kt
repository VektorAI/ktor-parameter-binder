package ai.vektor.ktor.binder.handlers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
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
                println("Suspend")
                handler.callSuspend(*args.toTypedArray())
            } else {
                println("Normal")
                handler.call(*args.toTypedArray())
            }
            call.respond(response)
        }
    }
}


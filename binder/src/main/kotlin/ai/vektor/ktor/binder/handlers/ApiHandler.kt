package ai.vektor.ktor.binder.handlers

import ai.vektor.ktor.binder.annotations.ApiMethod
import ai.vektor.ktor.binder.annotations.ApiPath
import ai.vektor.ktor.binder.annotations.Method
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpMethod
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.instanceParameter

data class ApiHandler(
    val path: String,
    val method: HttpMethod,
    val handler: KFunction<*>
)

fun Route.registerHandler(
    apiHandler: ApiHandler,
    binder: ParamBinder
) {
    val handler = apiHandler.handler
    route(apiHandler.path, apiHandler.method) {
        handle {
            val args = bindParams(binder, handler, this.call)

            val response = if (handler.isSuspend) {
                handler.callSuspendBy(args)
            } else {
                handler.callBy(args)
            }
            call.respond(response ?: Unit)
        }
    }
}

private fun mapMethod(method: Method): HttpMethod = when (method) {
    Method.GET -> HttpMethod.Get
    Method.POST -> HttpMethod.Post
    Method.DELETE -> HttpMethod.Delete
    Method.PATCH -> HttpMethod.Patch
    Method.PUT -> HttpMethod.Put
    Method.OPTIONS -> HttpMethod.Options
    Method.HEAD -> HttpMethod.Head
}

private fun mergePath(prefix: String, suffix: String) = "${prefix.trimEnd('/')}/${suffix.trimStart('/')}".trimEnd('/')

private suspend fun bindParams(
    binder: ParamBinder,
    handler: KFunction<*>,
    call: ApplicationCall,
    instance: Any? = null
): Map<KParameter, Any?> = handler.parameters.associate {
    if (handler.instanceParameter != null && handler.instanceParameter == it) {
        handler.instanceParameter!! to instance
    } else {
        it to binder.bind(it, call)
    }
}

fun Route.registerHandler(
    controller: Any,
    binder: ParamBinder
) {
    val prefix = controller::class.findAnnotation<ApiPath>()?.path ?: ""
    controller::class.declaredMemberFunctions.filter {
        it.hasAnnotation<ApiPath>() || it.hasAnnotation<ApiMethod>()
    }.map { function ->
        val method = function.findAnnotation<ApiMethod>()?.method ?: Method.GET
        val httpMethod = mapMethod(method)
        val suffix = function.findAnnotation<ApiPath>()?.path ?: ""
        val resultPath = mergePath(prefix, suffix)

        route(resultPath, httpMethod) {
            handle {
                val args = bindParams(binder, function, this.call, controller)

                val response = if (function.isSuspend) {
                    function.callSuspendBy(args)
                } else {
                    function.callBy(args)
                }
                call.respond(response ?: Unit)
            }
        }
    }
}

package ai.vektor.ktor.binder.demo

import ai.vektor.ktor.binder.demo.handlers.ApiController
import ai.vektor.ktor.binder.demo.handlers.handlerFour
import ai.vektor.ktor.binder.demo.handlers.handlerOne
import ai.vektor.ktor.binder.demo.handlers.handlerThree
import ai.vektor.ktor.binder.demo.handlers.handlerTwo
import ai.vektor.ktor.binder.demo.processors.UserParamProcessor
import ai.vektor.ktor.binder.demo.provider.UserProvider
import ai.vektor.ktor.binder.exceptions.ParamBindingException
import ai.vektor.ktor.binder.handlers.ApiHandler
import ai.vektor.ktor.binder.handlers.ParamBinder
import ai.vektor.ktor.binder.handlers.defaultProcessors
import ai.vektor.ktor.binder.handlers.registerHandler
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {

    val controller = ApiController(3)

    val handlers = listOf(
        ApiHandler("one", HttpMethod.Post, ::handlerOne),
        ApiHandler("two", HttpMethod.Post, ::handlerTwo),
        ApiHandler("three", HttpMethod.Put, ::handlerThree),
        ApiHandler("four", HttpMethod.Patch, ::handlerFour)
    )

    val userProvider = UserProvider()

    val userParamProcessor = UserParamProcessor(userProvider)

    val binder = ParamBinder(processors = defaultProcessors + userParamProcessor)

    embeddedServer(Netty, port = 8089) {
        routing {
            handlers.forEach {
                registerHandler(it, binder)
            }
            registerHandler(controller, binder)
        }
        install(ContentNegotiation) {
            jackson()
        }
        install(StatusPages) {
            exception<ParamBindingException> {
                log.error("Failed to bind a parameter", it)
                call.respond(HttpStatusCode.PreconditionFailed, it.message ?: "Failed to bind a parameter")
            }
        }
    }.start(wait = true)
}

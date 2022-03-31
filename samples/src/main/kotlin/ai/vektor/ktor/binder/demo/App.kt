package ai.vektor.ktor.binder.demo

import ai.vektor.ktor.binder.demo.handlers.Controller
import ai.vektor.ktor.binder.demo.handlers.handlerFive
import ai.vektor.ktor.binder.demo.handlers.handlerOne
import ai.vektor.ktor.binder.demo.handlers.handlerThree
import ai.vektor.ktor.binder.demo.handlers.handlerTwo
import ai.vektor.ktor.binder.demo.processors.UserParamProcessor
import ai.vektor.ktor.binder.demo.provider.UserProvider
import ai.vektor.ktor.binder.handlers.ApiHandler
import ai.vektor.ktor.binder.handlers.ParamBinder
import ai.vektor.ktor.binder.handlers.defaultProcessors
import ai.vektor.ktor.binder.handlers.registerHandler
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {

    val controller = Controller(3)

    val handlers = listOf(
        ApiHandler("one", HttpMethod.Post, ::handlerOne),
        ApiHandler("two", HttpMethod.Post, ::handlerTwo),
        ApiHandler("three", HttpMethod.Put, ::handlerThree),
        ApiHandler("four", HttpMethod.Patch, controller::handlerFour),
        ApiHandler("five", HttpMethod.Patch, ::handlerFive)
    )

    val userProvider = UserProvider()

    val userParamProcessor = UserParamProcessor(userProvider)

    val binder = ParamBinder(processors = defaultProcessors + userParamProcessor)

    embeddedServer(Netty, port = 8089) {
        routing {
            handlers.forEach {
                registerHandler(it, binder)
            }
        }
        install(ContentNegotiation) {
            jackson()
        }
    }.start(wait = true)
}
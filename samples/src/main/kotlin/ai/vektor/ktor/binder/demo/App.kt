package ai.vektor.ktor.binder.demo

import ai.vektor.ktor.binder.demo.handlers.*
import ai.vektor.ktor.binder.demo.processors.UserParamProcessor
import ai.vektor.ktor.binder.demo.provider.UserProvider
import ai.vektor.ktor.binder.handlers.ApiHandler
import ai.vektor.ktor.binder.handlers.ParamBinder
import ai.vektor.ktor.binder.handlers.defaultProcessors
import ai.vektor.ktor.binder.handlers.registerHandler
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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

fun main() {
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
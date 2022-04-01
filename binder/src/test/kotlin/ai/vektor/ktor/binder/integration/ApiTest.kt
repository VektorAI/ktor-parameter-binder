package ai.vektor.ktor.binder.integration

import ai.vektor.ktor.binder.handlers.ParamBinder
import ai.vektor.ktor.binder.handlers.defaultConverters
import ai.vektor.ktor.binder.handlers.defaultProcessors
import ai.vektor.ktor.binder.handlers.registerHandler
import ai.vektor.ktor.binder.integration.app.controllers.BookController
import ai.vektor.ktor.binder.integration.app.converters.ISBNConverter
import ai.vektor.ktor.binder.integration.app.models.ISBN
import ai.vektor.ktor.binder.integration.app.processors.UserParamProcessor
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withApplication
import org.junit.jupiter.api.Test
import kotlin.reflect.full.createType
import kotlin.test.assertEquals

class ApiTest {

    private val controllers = listOf(
        BookController()
    )

    private val converters = defaultConverters + mapOf(ISBN::class.createType() to ISBNConverter())
    private val processors = defaultProcessors + UserParamProcessor()

    private val binder = ParamBinder(converters = converters, processors = processors)

    private val env: ApplicationEngineEnvironment = applicationEngineEnvironment {
        module {
            routing {
                controllers.forEach { registerHandler(it, binder) }
            }
            install(ContentNegotiation) {
                jackson()
            }
        }
    }

    @Test
    fun testBookGetApi() = withApplication(env) {
        with(handleRequest(HttpMethod.Get, "/api/books/9780747532743")) {
            assertEquals(
                "{\"isbn\":{\"value\":9780747532743},\"title\":\"Harry Potter And The Philosopher's Stone\",\"author\":\"J. K. Rowling\"}",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testBookGetAllApi() = withApplication(env) {
        with(handleRequest(HttpMethod.Get, "/api/books")) {
            assertEquals(
                "[{\"isbn\":{\"value\":9780747532743},\"title\":\"Harry Potter And The Philosopher's Stone\",\"author\":\"J. K. Rowling\"},{\"isbn\":{\"value\":9781617293290},\"title\":\"Kotlin in Action\",\"author\":\"Isakova, Svetlana\"},{\"isbn\":{\"value\":9780060007768},\"title\":\"The Gulag Archipelago: 1918-1956\",\"author\":\"Aleksandr Solzhenitsyn\"},{\"isbn\":{\"value\":9780451191144},\"title\":\"Atlas Shrugged\",\"author\":\"Rand, Ayn\"}]",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testBookGetByFilterApi() = withApplication(env) {
        with(handleRequest(HttpMethod.Get, "/api/books?prefix=Harry")) {
            assertEquals(
                "[{\"isbn\":{\"value\":9780747532743},\"title\":\"Harry Potter And The Philosopher's Stone\",\"author\":\"J. K. Rowling\"}]",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testBookPostApi() = withApplication(env) {

        // Create book
        with(
            handleRequest(HttpMethod.Post, "/api/books") {
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(
                    "{\"isbn\":{\"value\":9781101990322},\"title\":\"Treasure Island\",\"author\":\"Stevenson, Robert Louis\"}",
                )
            }
        ) {
            assertEquals(
                "{\"isbn\":{\"value\":9781101990322},\"title\":\"Treasure Island\",\"author\":\"Stevenson, Robert Louis\"}",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }

        // Load book
        with(handleRequest(HttpMethod.Get, "/api/books/9781101990322")) {
            assertEquals(
                "{\"isbn\":{\"value\":9781101990322},\"title\":\"Treasure Island\",\"author\":\"Stevenson, Robert Louis\"}",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }

        // Delete book
        with(handleRequest(HttpMethod.Delete, "/api/books/9781101990322")) {
            assertEquals(null, response.content)
            assertEquals(HttpStatusCode.NoContent, response.status())
        }
    }

    @Test
    fun testBookDeleteApi() = withApplication(env) {
        with(
            handleRequest(HttpMethod.Post, "/api/books") {
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(
                    "{\"isbn\":{\"value\":9780134757599},\"title\":\"Refactoring: Improving the Design of Existing Code\",\"author\":\"Martin Fowler\"}",
                )
            }
        ) {
            assertEquals(
                "{\"isbn\":{\"value\":9780134757599},\"title\":\"Refactoring: Improving the Design of Existing Code\",\"author\":\"Martin Fowler\"}",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }

        with(handleRequest(HttpMethod.Delete, "/api/books/9780134757599")) {
            assertEquals(null, response.content)
            assertEquals(HttpStatusCode.NoContent, response.status())
        }
    }

    @Test
    fun testBuyBookApi() = withApplication(env) {
        with(
            handleRequest(HttpMethod.Post, "/api/books/9780747532743/purchases") {
                addHeader("X-User-Id", "1")
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody("{}")
            }
        ) {
            assertEquals(
                "{\"buyer\":{\"id\":1},\"book\":{\"isbn\":{\"value\":9780747532743},\"title\":\"Harry Potter And The Philosopher's Stone\",\"author\":\"J. K. Rowling\"}}",
                response.content
            )
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testBuyNotFoundBookApi() = withApplication(env) {
        with(
            handleRequest(HttpMethod.Post, "/api/books/9780747532744/purchases") {
                addHeader("X-User-Id", "1")
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody("{}")
            }
        ) {
            assertEquals(
                null,
                response.content
            )
            assertEquals(HttpStatusCode.NotFound, response.status())
        }
    }
}

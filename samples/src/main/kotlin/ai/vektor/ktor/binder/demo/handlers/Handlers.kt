package ai.vektor.ktor.binder.demo.handlers

import ai.vektor.ktor.binder.annotations.Body
import ai.vektor.ktor.binder.annotations.HeaderParam
import ai.vektor.ktor.binder.annotations.QueryParam
import ai.vektor.ktor.binder.demo.annotations.UserParam
import ai.vektor.ktor.binder.demo.model.Input
import ai.vektor.ktor.binder.demo.model.User
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

val log: Logger = LoggerFactory.getLogger("Handlers")

fun handlerOne(
    @QueryParam("param") a: Int,
    @HeaderParam("Custom-Header") b: Double,
    @Body input: Input
): String {
    log.info("${input.message} = ${a * b}")
    return "${input.message} = ${a * b}"
}

suspend fun handlerTwo(
    @QueryParam("param") a: String,
    @HeaderParam("Custom-Header") b: String,
    @Body input: Input
): String {
    log.info("$a $b $input")
    delay(1000)
    return "OK"
}

suspend fun handlerThree(
    @QueryParam("param") a: UUID,
    @HeaderParam("Custom-Header") b: String,
    @Body input: Input
): String {
    log.info("$a $b $input")
    delay(1000)
    return "OK"
}

suspend fun handlerFour(
    @QueryParam("param") a: Int,
    @HeaderParam("Custom-Header") b: String,
    @Body input: Input,
    @UserParam user: User
): User {
    log.info("$a $b $input $user")
    delay(1000)
    return user
}

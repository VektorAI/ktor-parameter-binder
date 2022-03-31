package org.buldakov.demo

import ai.vektor.ktor.binder.annotations.Body
import ai.vektor.ktor.binder.annotations.HeaderParam
import ai.vektor.ktor.binder.annotations.QueryParam
import kotlinx.coroutines.delay
import java.util.*

fun handlerOne(
    @QueryParam("param") a: Int,
    @HeaderParam("Custom-Header") b: Double,
    @Body input: Input
): String {
    log.info("$a $b $input")
    return "OK"
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

suspend fun handlerFive(
    @QueryParam("param") a: Int,
    @HeaderParam("Custom-Header") b: String,
    @Body input: Input,
    @UserParam user: User
): String {
    log.info("$a $b $input $user")
    delay(1000)
    return "OK"
}

class Controller(private val factor: Int) {

    fun handlerFour(
        @QueryParam("param") a: Int,
        @HeaderParam("Custom-Header") b: Double,
        @Body input: Input
    ): String {
        log.info("param * $factor = ${a * factor}, $b $input")
        return "OK"
    }
}
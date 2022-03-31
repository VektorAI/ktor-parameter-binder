package ai.vektor.ktor.binder.demo.handlers

import ai.vektor.ktor.binder.annotations.ApiMethod
import ai.vektor.ktor.binder.annotations.ApiPath
import ai.vektor.ktor.binder.annotations.Body
import ai.vektor.ktor.binder.annotations.HeaderParam
import ai.vektor.ktor.binder.annotations.Method
import ai.vektor.ktor.binder.annotations.QueryParam
import ai.vektor.ktor.binder.demo.model.Input

@ApiPath("/tests")
class ApiController(private val factor: Int) {

    @ApiMethod(Method.POST)
    fun postTest(
        @QueryParam("param") a: Int,
        @HeaderParam("Custom-Header") b: Double,
        @Body input: Input
    ): String {
        log.info("POST param * $factor = ${a * factor}, $b $input")
        return "OK"
    }

    @ApiMethod(Method.PUT)
    fun putTest(
        @QueryParam("param") a: Int,
        @HeaderParam("Custom-Header") b: Double,
        @Body input: Input
    ): String {
        log.info("PUT param * $factor = ${a * factor}, $b $input")
        return "OK"
    }

    @ApiMethod(Method.PUT)
    @ApiPath("/{id}/extra")
    fun extraTest(
        @QueryParam("param") a: Int,
        @QueryParam("id") id: Int,
        @HeaderParam("Custom-Header") b: Double,
        @Body input: Input
    ): String {
        log.info("PUT extra param * $factor = ${a * factor}, $b $input, $id")
        return "OK"
    }
}

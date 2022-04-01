package ai.vektor.ktor.binder.integration.app.controllers

import ai.vektor.ktor.binder.annotations.ApiPath
import ai.vektor.ktor.binder.annotations.Auth

@Auth
@ApiPath("/api/auth")
class AuthController {

    @ApiPath("/simple")
    fun testSimple(): String {
        return "OK"
    }

    @Auth("admin")
    @ApiPath("/override")
    fun testOverride(): String {
        return "OK"
    }


}
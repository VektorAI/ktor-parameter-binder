package ai.vektor.ktor.binder.integration.app.controllers

import ai.vektor.ktor.binder.annotations.ApiPath
import ai.vektor.ktor.binder.annotations.QueryParam
import ai.vektor.ktor.binder.integration.app.models.User

@ApiPath("/api/users")
class UserController {

    @ApiPath("{id}")
    fun getUser(@QueryParam("id") id: Int): User {
        return User(id)
    }

}
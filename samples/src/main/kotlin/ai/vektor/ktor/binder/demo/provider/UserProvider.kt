package ai.vektor.ktor.binder.demo.provider

import ai.vektor.ktor.binder.demo.model.User

class UserProvider {

    fun getUserById(id: Int) = User(id)
}
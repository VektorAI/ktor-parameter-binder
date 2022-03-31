package ai.vektor.ktor.binder.demo

import ai.vektor.ktor.binder.demo.User

class UserProvider {

    fun getUserById(id: Int) = User(id)
}
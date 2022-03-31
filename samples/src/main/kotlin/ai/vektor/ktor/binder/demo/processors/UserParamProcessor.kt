package ai.vektor.ktor.binder.demo.processors

import ai.vektor.ktor.binder.demo.annotations.UserParam
import ai.vektor.ktor.binder.demo.model.User
import ai.vektor.ktor.binder.demo.provider.UserProvider
import ai.vektor.ktor.binder.processors.ParamProcessor
import io.ktor.application.ApplicationCall
import kotlin.reflect.KParameter
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation

class UserParamProcessor(private val userProvider: UserProvider) : ParamProcessor {

    override fun canProcess(param: KParameter) =
        param.findAnnotation<UserParam>() != null && (param.type == User::class.createType())

    override suspend fun process(call: ApplicationCall, param: KParameter) =
        call.request.headers["Authorization"]?.toIntOrNull()?.let {
            userProvider.getUserById(it)
        }

}
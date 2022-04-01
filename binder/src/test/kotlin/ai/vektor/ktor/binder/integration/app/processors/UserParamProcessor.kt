package ai.vektor.ktor.binder.integration.app.processors

import ai.vektor.ktor.binder.integration.app.annotations.UserParam
import ai.vektor.ktor.binder.integration.app.models.User
import ai.vektor.ktor.binder.processors.ParamProcessor
import io.ktor.application.ApplicationCall
import kotlin.reflect.KParameter
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation

class UserParamProcessor : ParamProcessor {

    override fun canProcess(param: KParameter) =
        param.findAnnotation<UserParam>() != null && (param.type == User::class.createType())

    override suspend fun process(call: ApplicationCall, param: KParameter) =
        call.request.headers["X-User-Id"]?.toIntOrNull()?.let {
            User(it)
        }
}

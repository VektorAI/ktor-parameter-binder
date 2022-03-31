package ai.vektor.ktor.binder.processors

import ai.vektor.ktor.binder.annotations.Body
import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation

class BodyProcessor : ParamProcessor {
    override fun canProcess(param: KParameter) = param.findAnnotation<Body>() != null

    override suspend fun process(call: ApplicationCall, param: KParameter): Any = call.receive(param.type)
}

package ai.vektor.ktor.binder.processors

import ai.vektor.ktor.binder.annotations.HeaderParam
import io.ktor.application.ApplicationCall
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation

class HeaderParamProcessor : ParamProcessor {

    override fun canProcess(param: KParameter) = param.findAnnotation<HeaderParam>() != null

    override suspend fun process(call: ApplicationCall, param: KParameter) =
        param.findAnnotation<HeaderParam>()?.let { call.request.headers[it.headerName] }
}

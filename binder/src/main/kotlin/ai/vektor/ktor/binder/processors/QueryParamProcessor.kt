package ai.vektor.ktor.binder.processors

import ai.vektor.ktor.binder.annotations.QueryParam
import io.ktor.application.ApplicationCall
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation

class QueryParamProcessor : ParamProcessor {

    override suspend fun process(call: ApplicationCall, param: KParameter): Any? =
        param.findAnnotation<QueryParam>()?.let { call.parameters[it.paramName] }

    override fun canProcess(param: KParameter) = param.findAnnotation<QueryParam>() != null
}

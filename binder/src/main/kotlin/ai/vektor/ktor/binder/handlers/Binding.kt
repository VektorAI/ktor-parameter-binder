package ai.vektor.ktor.binder.handlers

import ai.vektor.ktor.binder.converters.*
import ai.vektor.ktor.binder.processors.BodyProcessor
import ai.vektor.ktor.binder.processors.HeaderParamProcessor
import ai.vektor.ktor.binder.processors.ParamProcessor
import ai.vektor.ktor.binder.processors.QueryParamProcessor
import io.ktor.application.*
import io.ktor.util.reflect.*
import java.util.*
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.jvm.jvmErasure

val defaultProcessors = listOf(
    HeaderParamProcessor(),
    QueryParamProcessor(),
    BodyProcessor()
)

val defaultConverters = mapOf(
    Int::class.createType() to IntConverter(),
    Long::class.createType() to LongConverter(),
    Double::class.createType() to DoubleConverter(),
    UUID::class.createType() to UUIDConverter(),
)

class ParamBinder(
    private val processors: List<ParamProcessor> = defaultProcessors,
    private val converters: Map<KType, ParamConverter<*>> = defaultConverters
) {

    suspend fun bind(param: KParameter, call: ApplicationCall) =
        processors.firstOrNull { it.canProcess(param) }?.process(call, param)?.let {
            if (it.instanceOf(param.type.jvmErasure)) {
                it
            } else when (it) {
                is String -> converters[param.type]?.convert(it)
                else -> null
            }
        }
}

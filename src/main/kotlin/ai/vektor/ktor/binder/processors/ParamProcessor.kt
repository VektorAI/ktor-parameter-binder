package ai.vektor.ktor.binder.processors

import io.ktor.application.*
import kotlin.reflect.KParameter

interface ParamProcessor {

    fun canProcess(param: KParameter): Boolean

    suspend fun process(call: ApplicationCall, param: KParameter): Any?
}
package org.buldakov.demo

import ai.vektor.ktor.binder.processors.ParamProcessor
import io.ktor.application.*
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
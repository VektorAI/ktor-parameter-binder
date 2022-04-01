package ai.vektor.ktor.binder.integration.app.converters

import ai.vektor.ktor.binder.converters.ParamConverter
import ai.vektor.ktor.binder.integration.app.models.ISBN

class ISBNConverter : ParamConverter<ISBN> {
    override fun convert(value: String): ISBN? = value.toLongOrNull()?.let { ISBN(it) }
}

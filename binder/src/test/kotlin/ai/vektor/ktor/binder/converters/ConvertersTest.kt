package ai.vektor.ktor.binder.converters

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConvertersTest {

    @Test
    fun testIntConverter() {
        val intConverter = IntConverter()

        assertEquals(1, intConverter.convert("1"))
        assertEquals(null, intConverter.convert("abc"))
        assertEquals(-1, intConverter.convert("-1"))
        assertEquals(null, intConverter.convert("2147483648"))
        assertEquals(2147483647, intConverter.convert("2147483647"))
        assertEquals(null, intConverter.convert("-2147483649"))
        assertEquals(-2147483648, intConverter.convert("-2147483648"))
    }
}

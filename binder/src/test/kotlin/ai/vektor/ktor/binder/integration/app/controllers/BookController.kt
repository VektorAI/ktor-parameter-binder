package ai.vektor.ktor.binder.integration.app.controllers

import ai.vektor.ktor.binder.annotations.ApiMethod
import ai.vektor.ktor.binder.annotations.ApiPath
import ai.vektor.ktor.binder.annotations.Body
import ai.vektor.ktor.binder.annotations.Method
import ai.vektor.ktor.binder.annotations.QueryParam
import ai.vektor.ktor.binder.integration.app.annotations.UserParam
import ai.vektor.ktor.binder.integration.app.models.Book
import ai.vektor.ktor.binder.integration.app.models.ISBN
import ai.vektor.ktor.binder.integration.app.models.Purchase
import ai.vektor.ktor.binder.integration.app.models.User
import ai.vektor.ktor.binder.response.ResponseEntity

@ApiPath("/api/books")
class BookController {

    private val books = mutableListOf(
        Book(ISBN(9780747532743), "Harry Potter And The Philosopher's Stone", "J. K. Rowling"),
        Book(ISBN(9781617293290), title = "Kotlin in Action", "Isakova, Svetlana"),
        Book(ISBN(9780060007768), "The Gulag Archipelago: 1918-1956", "Aleksandr Solzhenitsyn"),
        Book(ISBN(9780451191144), "Atlas Shrugged", "Rand, Ayn")
    )

    @ApiPath
    fun getBooks(@QueryParam("prefix") prefix: String?): List<Book> {
        if (prefix == null) {
            return books
        }
        return books.filter { it.title.startsWith(prefix) }
    }

    @ApiPath("/{id}")
    fun getBook(@QueryParam("id") id: ISBN): Book? {
        return books.firstOrNull { it.isbn == id }
    }

    @ApiMethod(Method.POST)
    fun postBook(@Body book: Book): Book {
        books.add(book)
        return book
    }

    @ApiMethod(Method.DELETE)
    @ApiPath("/{id}")
    fun deleteBook(@QueryParam("id") id: ISBN) {
        books.removeIf { it.isbn == id }
    }

    @ApiMethod(Method.POST)
    @ApiPath("/{id}/purchases")
    fun buyBook(@QueryParam("id") id: ISBN, @UserParam user: User): ResponseEntity {
        val book = books.firstOrNull { it.isbn == id } ?: return ResponseEntity.notFound()
        return ResponseEntity.ok(Purchase(user, book))
    }
}

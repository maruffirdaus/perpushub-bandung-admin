package com.perpushub.bandung.admin.data.repository

import com.perpushub.bandung.admin.common.model.Book
import com.perpushub.bandung.admin.common.model.BookCopy
import com.perpushub.bandung.admin.common.model.BookDetail
import com.perpushub.bandung.admin.data.remote.BookService
import com.perpushub.bandung.admin.data.remote.model.request.AddBookCopyRequest
import com.perpushub.bandung.admin.data.remote.model.request.AddBookRequest

class BookRepository(
    private val service: BookService
) {
    suspend fun addBook(isbn: String): Int {
        val request = AddBookRequest(isbn)
        val response = service.addBook(request)
        return response.data ?: throw Exception(response.message)
    }

    suspend fun getBooks(query: String): List<Book> {
        val response = service.getBooks(query)
        return response.data ?: throw Exception(response.message)
    }

    suspend fun getBookDetail(id: Int): BookDetail {
        val response = service.getBookDetail(id)
        return response.data ?: throw Exception(response.message)
    }

    suspend fun addBookCopy(bookId: Int, libraryId: Int) {
        val request = AddBookCopyRequest(bookId, libraryId)
        val response = service.addBookCopy(request)
        if (response.status != "success") {
            throw Exception(response.message)
        }
    }

    suspend fun getBookCopies(id: Int): List<BookCopy> {
        val response = service.getBookCopies(id)
        return response.data ?: throw Exception(response.message)
    }
}
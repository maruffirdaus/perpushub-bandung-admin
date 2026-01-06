package com.perpushub.bandung.admin.data.remote

import com.perpushub.bandung.admin.BuildConfig
import com.perpushub.bandung.admin.data.remote.model.request.AddBookCopyRequest
import com.perpushub.bandung.admin.data.remote.model.request.AddBookRequest
import com.perpushub.bandung.admin.data.remote.model.response.AddBookResponse
import com.perpushub.bandung.admin.data.remote.model.response.GetBookCopiesResponse
import com.perpushub.bandung.admin.data.remote.model.response.GetBookDetailResponse
import com.perpushub.bandung.admin.data.remote.model.response.GetBooksResponse
import com.perpushub.bandung.admin.data.remote.model.response.StatusResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class BookService(
    private val client: HttpClient
) {
    private val baseUrl = BuildConfig.BACKEND_BASE_URL

    suspend fun addBook(request: AddBookRequest): AddBookResponse {
        val response = client.post("$baseUrl/books") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getBooks(query: String): GetBooksResponse {
        val response = client.get("$baseUrl/books") {
            parameter("q", query)
        }
        return response.body()
    }

    suspend fun getBookDetail(id: Int): GetBookDetailResponse {
        val response = client.get("$baseUrl/books/$id")
        return response.body()
    }

    suspend fun addBookCopy(request: AddBookCopyRequest): StatusResponse {
        val response = client.post("$baseUrl/books/copies") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getBookCopies(id: Int): GetBookCopiesResponse {
        val response = client.get("$baseUrl/books/$id/copies")
        return response.body()
    }
}
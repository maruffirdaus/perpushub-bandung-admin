package com.perpushub.bandung.admin.data.remote

import com.perpushub.bandung.admin.BuildConfig
import com.perpushub.bandung.admin.data.remote.model.request.ApproveSubmittedRequest
import com.perpushub.bandung.admin.data.remote.model.response.GetSubmittedResponse
import com.perpushub.bandung.admin.data.remote.model.response.StatusResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LoanRequestService(
    private val client: HttpClient
) {
    private val baseUrl = BuildConfig.BACKEND_BASE_URL

    suspend fun getSubmitted(): GetSubmittedResponse {
        val response = client.get("$baseUrl/loan-requests/submitted/admin")
        return response.body()
    }

    suspend fun approveSubmitted(id: Int, request: ApproveSubmittedRequest): StatusResponse {
        val response = client.post("$baseUrl/loan-requests/$id/approve") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun rejectSubmitted(id: Int): StatusResponse {
        val response = client.post("$baseUrl/loan-requests/$id/reject")
        return response.body()
    }
}
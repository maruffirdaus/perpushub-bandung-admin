package com.perpushub.bandung.admin.data.remote

import com.perpushub.bandung.admin.BuildConfig
import com.perpushub.bandung.admin.data.remote.model.response.GetSubmittedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LoanRequestService(
    private val client: HttpClient
) {
    private val baseUrl = BuildConfig.BACKEND_BASE_URL

    suspend fun getSubmitted(): GetSubmittedResponse {
        val response = client.get("$baseUrl/loan-requests/submitted/admin")
        return response.body()
    }
}
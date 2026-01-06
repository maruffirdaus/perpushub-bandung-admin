package com.perpushub.bandung.admin.data.remote

import com.perpushub.bandung.admin.BuildConfig
import com.perpushub.bandung.admin.data.remote.model.response.GetLoansResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LoanService(
    private val client: HttpClient
) {
    private val baseUrl = BuildConfig.BACKEND_BASE_URL

    suspend fun getInDelivery(): GetLoansResponse {
        val response = client.get("$baseUrl/loans/in-delivery/admin")
        return response.body()
    }

    suspend fun getBorrowed(): GetLoansResponse {
        val response = client.get("$baseUrl/loans/borrowed/admin")
        return response.body()
    }
}
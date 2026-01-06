package com.perpushub.bandung.admin.data.repository

import com.perpushub.bandung.admin.common.model.LoanRequest
import com.perpushub.bandung.admin.data.remote.LoanRequestService

class LoanRequestRepository(
    private val service: LoanRequestService
) {
    suspend fun getSubmitted(): List<LoanRequest> {
        val response = service.getSubmitted()
        return response.data ?: throw Exception(response.message)
    }
}
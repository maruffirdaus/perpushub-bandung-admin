package com.perpushub.bandung.admin.data.repository

import com.perpushub.bandung.admin.common.model.LoanRequest
import com.perpushub.bandung.admin.data.remote.LoanRequestService
import com.perpushub.bandung.admin.data.remote.model.request.ApproveSubmittedRequest

class LoanRequestRepository(
    private val service: LoanRequestService
) {
    suspend fun getSubmitted(): List<LoanRequest> {
        val response = service.getSubmitted()
        return response.data ?: throw Exception(response.message)
    }

    suspend fun approveSubmitted(id: Int, bookCopyId: Int, dueDate: String) {
        val request = ApproveSubmittedRequest(bookCopyId, dueDate)
        val response = service.approveSubmitted(id, request)
        if (response.status != "success") {
            throw Exception(response.message)
        }
    }

    suspend fun rejectSubmitted(id: Int) {
        val response = service.rejectSubmitted(id)
        if (response.status != "success") {
            throw Exception(response.message)
        }
    }
}
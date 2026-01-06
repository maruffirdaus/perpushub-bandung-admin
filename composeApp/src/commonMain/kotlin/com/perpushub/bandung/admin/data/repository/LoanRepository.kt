package com.perpushub.bandung.admin.data.repository

import com.perpushub.bandung.admin.common.model.Loan
import com.perpushub.bandung.admin.data.remote.LoanService

class LoanRepository(
    private val service: LoanService
) {
    suspend fun getInDelivery(): List<Loan> {
        val response = service.getInDelivery()
        return response.data ?: throw Exception(response.message)
    }

    suspend fun deliverLoan(id: Int) {
        val response = service.deliverLoan(id)
        if (response.status != "success") {
            throw Exception(response.message)
        }
    }

    suspend fun getBorrowed(): List<Loan> {
        val response = service.getBorrowed()
        return response.data ?: throw Exception(response.message)
    }

    suspend fun returnLoan(id: Int) {
        val response = service.returnLoan(id)
        if (response.status != "success") {
            throw Exception(response.message)
        }
    }
}
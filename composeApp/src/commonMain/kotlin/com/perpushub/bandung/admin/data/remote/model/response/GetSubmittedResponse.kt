package com.perpushub.bandung.admin.data.remote.model.response

import com.perpushub.bandung.admin.common.model.LoanRequest
import kotlinx.serialization.Serializable

@Serializable
data class GetSubmittedResponse(
    val status: String,
    val message: String,
    val data: List<LoanRequest>?
)
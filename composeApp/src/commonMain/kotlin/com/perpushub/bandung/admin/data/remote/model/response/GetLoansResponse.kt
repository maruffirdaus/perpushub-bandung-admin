package com.perpushub.bandung.admin.data.remote.model.response

import com.perpushub.bandung.admin.common.model.Loan
import kotlinx.serialization.Serializable

@Serializable
data class GetLoansResponse(
    val status: String,
    val message: String,
    val data: List<Loan>?
)

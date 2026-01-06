package com.perpushub.bandung.admin.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ApproveSubmittedRequest(
    val bookCopyId: Int,
    val dueDate: String
)

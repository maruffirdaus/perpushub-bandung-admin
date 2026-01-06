package com.perpushub.bandung.admin.data.remote.model.response

import com.perpushub.bandung.admin.common.model.BookDetail
import kotlinx.serialization.Serializable

@Serializable
data class GetBookDetailResponse(
    val status: String,
    val message: String,
    val data: BookDetail?
)

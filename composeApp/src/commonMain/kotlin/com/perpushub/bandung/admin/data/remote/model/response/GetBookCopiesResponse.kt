package com.perpushub.bandung.admin.data.remote.model.response

import com.perpushub.bandung.admin.common.model.BookCopy
import kotlinx.serialization.Serializable

@Serializable
data class GetBookCopiesResponse(
    val status: String,
    val message: String,
    val data: List<BookCopy>?
)

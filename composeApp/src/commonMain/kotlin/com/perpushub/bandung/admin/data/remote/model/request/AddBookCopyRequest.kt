package com.perpushub.bandung.admin.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddBookCopyRequest(
    val bookId: Int,
    val libraryId: Int
)

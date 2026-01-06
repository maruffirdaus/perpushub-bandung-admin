package com.perpushub.bandung.admin.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AddBookResponse(
    val status: String,
    val message: String,
    val data: Int?
)

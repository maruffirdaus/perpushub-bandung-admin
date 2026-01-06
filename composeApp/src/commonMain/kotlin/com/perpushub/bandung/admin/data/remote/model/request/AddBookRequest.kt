package com.perpushub.bandung.admin.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddBookRequest(
    val isbn: String
)
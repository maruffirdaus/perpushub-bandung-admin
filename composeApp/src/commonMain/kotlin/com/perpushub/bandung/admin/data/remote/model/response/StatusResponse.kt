package com.perpushub.bandung.admin.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val status: String,
    val message: String
)
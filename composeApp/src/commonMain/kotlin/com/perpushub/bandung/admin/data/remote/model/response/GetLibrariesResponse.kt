package com.perpushub.bandung.admin.data.remote.model.response

import com.perpushub.bandung.admin.common.model.LibraryDetail
import kotlinx.serialization.Serializable

@Serializable
data class GetLibrariesResponse(
    val status: String,
    val message: String,
    val data: List<LibraryDetail>?
)
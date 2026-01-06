package com.perpushub.bandung.admin.data.repository

import com.perpushub.bandung.admin.common.model.LibraryDetail
import com.perpushub.bandung.admin.data.remote.LibraryService

class LibraryRepository(
    private val service: LibraryService
) {
    suspend fun getLibraries(): List<LibraryDetail> {
        val response = service.getLibraries()
        return response.data ?: throw Exception(response.message)
    }
}
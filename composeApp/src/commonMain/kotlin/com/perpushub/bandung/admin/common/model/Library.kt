package com.perpushub.bandung.admin.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val id: Int,
    val name: String
) {
    companion object {
        val dummies = LibraryDetail.Companion.dummies.map {
            Library(
                id = it.id,
                name = it.name
            )
        }
    }
}

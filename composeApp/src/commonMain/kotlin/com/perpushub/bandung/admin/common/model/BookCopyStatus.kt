package com.perpushub.bandung.admin.common.model

enum class BookCopyStatus(val label: String) {
    AVAILABLE("Tersedia"),
    BORROWED("Dipinjam"),
    LOST("Hilang"),
    DAMAGED("Rusak")
}
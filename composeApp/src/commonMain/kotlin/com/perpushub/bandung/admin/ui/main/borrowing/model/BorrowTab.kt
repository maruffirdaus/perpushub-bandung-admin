package com.perpushub.bandung.admin.ui.main.borrowing.model

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Bookmark
import io.github.composefluent.icons.regular.ClipboardTask
import io.github.composefluent.icons.regular.VehicleTruckProfile

enum class BorrowTab(val icon: ImageVector, val label: String) {
    REQUESTS(Icons.Regular.ClipboardTask, "Pengajuan"),
    DELIVERY(Icons.Regular.VehicleTruckProfile, "Dikirim"),
    BORROWED(Icons.Regular.Bookmark, "Dipinjam")
}
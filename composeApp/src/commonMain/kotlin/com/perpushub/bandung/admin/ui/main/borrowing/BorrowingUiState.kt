package com.perpushub.bandung.admin.ui.main.borrowing

import com.perpushub.bandung.admin.common.model.Address
import com.perpushub.bandung.admin.common.model.BookCopy
import com.perpushub.bandung.admin.common.model.LibraryDetail
import com.perpushub.bandung.admin.common.model.Loan
import com.perpushub.bandung.admin.common.model.LoanRequest
import com.perpushub.bandung.admin.ui.main.borrowing.model.BorrowTab

data class BorrowingUiState(
    val selectedTab: BorrowTab = BorrowTab.REQUESTS,
    val requests: List<LoanRequest> = listOf(),
    val deliveries: List<Loan> = listOf(),
    val loans: List<Loan> = listOf(),
    val isLoading: Boolean = false
)

package com.perpushub.bandung.admin.ui.main.borrowing

import com.perpushub.bandung.admin.ui.main.borrowing.model.BorrowTab

sealed class BorrowingEvent {
    class OnSelectedTabChange(val tab: BorrowTab) : BorrowingEvent()
    object OnRequestsRefresh : BorrowingEvent()
    object OnDeliveriesRefresh : BorrowingEvent()
    object OnLoansRefresh : BorrowingEvent()
    class OnLoanRequestApprove(val id: Int) : BorrowingEvent()
    class OnLoanRequestReject(val id: Int) : BorrowingEvent()
    class OnBookDeliver(val id: Int) : BorrowingEvent()
    class OnBookReturn(val id: Int) : BorrowingEvent()
}
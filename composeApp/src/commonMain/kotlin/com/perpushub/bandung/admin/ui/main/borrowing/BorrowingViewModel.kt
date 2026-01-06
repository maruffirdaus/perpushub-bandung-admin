package com.perpushub.bandung.admin.ui.main.borrowing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpushub.bandung.admin.data.repository.LoanRepository
import com.perpushub.bandung.admin.data.repository.LoanRequestRepository
import com.perpushub.bandung.admin.ui.common.messaging.UiError
import com.perpushub.bandung.admin.ui.common.messaging.UiMessageManager
import com.perpushub.bandung.admin.ui.main.borrowing.model.BorrowTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BorrowingViewModel(
    private val loanRequestRepository: LoanRequestRepository,
    private val loanRepository: LoanRepository,
    private val uiMessageManager: UiMessageManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(BorrowingUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: BorrowingEvent) {
        when (event) {
            is BorrowingEvent.OnSelectedTabChange -> changeSelectedTab(event.tab)
            is BorrowingEvent.OnRequestsRefresh -> refreshRequests()
            is BorrowingEvent.OnDeliveriesRefresh -> refreshDeliveries()
            is BorrowingEvent.OnLoansRefresh -> refreshLoans()
            is BorrowingEvent.OnLoanRequestApprove -> approveLoanRequest(event.id)
            is BorrowingEvent.OnLoanRequestReject -> rejectLoanRequest(event.id)
            is BorrowingEvent.OnBookDeliver -> deliverBook(event.id)
            is BorrowingEvent.OnBookReturn -> returnBook(event.id)
        }
    }

    private fun changeSelectedTab(tab: BorrowTab) {
        _uiState.update {
            it.copy(selectedTab = tab)
        }
    }

    private fun refreshRequests() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                _uiState.update {
                    it.copy(requests = loanRequestRepository.getSubmitted())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun refreshDeliveries() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                _uiState.update {
                    it.copy(deliveries = loanRepository.getInDelivery())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun refreshLoans() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {
                _uiState.update {
                    it.copy(loans = loanRepository.getBorrowed())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun approveLoanRequest(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {

                _uiState.update {
                    it.copy(deliveries = loanRepository.getInDelivery())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun rejectLoanRequest(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {

                _uiState.update {
                    it.copy(deliveries = loanRepository.getInDelivery())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun deliverBook(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {

                _uiState.update {
                    it.copy(deliveries = loanRepository.getInDelivery())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun returnBook(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            try {

                _uiState.update {
                    it.copy(deliveries = loanRepository.getInDelivery())
                }
            } catch (e: Exception) {
                uiMessageManager.emitMessage(UiError(e.message ?: "Unknown error."))
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}
package com.perpushub.bandung.admin.ui.main.borrowing

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.perpushub.bandung.admin.common.model.Loan
import com.perpushub.bandung.admin.common.model.LoanRequest
import com.perpushub.bandung.admin.ui.main.borrowing.component.BorrowedItem
import com.perpushub.bandung.admin.ui.main.borrowing.component.DeliveryItem
import com.perpushub.bandung.admin.ui.main.borrowing.component.RequestsItem
import com.perpushub.bandung.admin.ui.main.borrowing.model.BorrowTab
import com.perpushub.bandung.admin.ui.main.common.extension.alignHorizontalSpace
import com.perpushub.bandung.admin.ui.navigation.main.MainNavKey
import com.perpushub.bandung.admin.ui.theme.AppTheme
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.Icon
import io.github.composefluent.component.ProgressRing
import io.github.composefluent.component.ScrollbarContainer
import io.github.composefluent.component.SelectorBarItem
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberScrollbarAdapter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BorrowingScreen(
    onNavigate: (NavKey) -> Unit,
    viewModel: BorrowingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BorrowingScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigate = onNavigate
    )
}

@Composable
fun BorrowingScreenContent(
    uiState: BorrowingUiState,
    onEvent: (BorrowingEvent) -> Unit,
    onNavigate: (NavKey) -> Unit
) {
    LaunchedEffect(uiState.selectedTab) {
        when (uiState.selectedTab) {
            BorrowTab.REQUESTS -> onEvent(BorrowingEvent.OnRequestsRefresh)
            BorrowTab.DELIVERY -> onEvent(BorrowingEvent.OnDeliveriesRefresh)
            BorrowTab.BORROWED -> onEvent(BorrowingEvent.OnLoansRefresh)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val lazyListState = rememberLazyListState()

        Spacer(Modifier.height(32.dp))
        Text(
            text = "Peminjaman",
            modifier = Modifier
                .alignHorizontalSpace()
                .padding(horizontal = 32.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = FluentTheme.typography.title
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.alignHorizontalSpace(),
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState)
        ) {
            items(BorrowTab.entries) { tab ->
                SelectorBarItem(
                    selected = uiState.selectedTab == tab,
                    onSelectedChange = {
                        onEvent(BorrowingEvent.OnSelectedTabChange(tab))
                    },
                    text = {
                        Text(tab.label)
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label
                        )
                    }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ProgressRing()
            }
        } else {
            when (uiState.selectedTab) {
                BorrowTab.REQUESTS -> RequestsSection(
                    requests = uiState.requests,
                    onEvent = onEvent,
                    onNavigate = onNavigate
                )

                BorrowTab.DELIVERY -> DeliverySection(
                    deliveries = uiState.deliveries,
                    onEvent = onEvent,
                    onNavigate = onNavigate
                )

                BorrowTab.BORROWED -> BorrowedSection(
                    loans = uiState.loans,
                    onEvent = onEvent,
                    onNavigate = onNavigate
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.RequestsSection(
    requests: List<LoanRequest>,
    onEvent: (BorrowingEvent) -> Unit,
    onNavigate: (NavKey) -> Unit
) {
    val lazyListState = rememberLazyListState()

    ScrollbarContainer(
        adapter = rememberScrollbarAdapter(lazyListState),
        modifier = Modifier.weight(1f)
    ) {
        LazyColumn(
            modifier = Modifier
                .alignHorizontalSpace()
                .fillMaxHeight(),
            state = lazyListState,
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
        ) {
            if (requests.isEmpty()) {
                item {
                    Text("Belum ada pengajuan buku.")
                }
            } else {
                itemsIndexed(requests) { index, loanRequest ->
                    RequestsItem(
                        loanRequest = loanRequest,
                        onItemClick = {
                            onNavigate(MainNavKey.BookDetail(loanRequest.book.id))
                        },
                        onApproveClick = {
                            onEvent(BorrowingEvent.OnLoanRequestApprove(loanRequest.id))
                        },
                        onRejectClick = {
                            onEvent(BorrowingEvent.OnLoanRequestReject(loanRequest.id))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        alternate = index % 2 == 0
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.DeliverySection(
    deliveries: List<Loan>,
    onEvent: (BorrowingEvent) -> Unit,
    onNavigate: (NavKey) -> Unit
) {
    val lazyListState = rememberLazyListState()

    ScrollbarContainer(
        adapter = rememberScrollbarAdapter(lazyListState),
        modifier = Modifier.weight(1f)
    ) {
        LazyColumn(
            modifier = Modifier
                .alignHorizontalSpace()
                .fillMaxHeight(),
            state = lazyListState,
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
        ) {
            if (deliveries.isEmpty()) {
                item {
                    Text("Belum ada buku yang dikirim.")
                }
            } else {
                itemsIndexed(deliveries) { index, loan ->
                    DeliveryItem(
                        loan = loan,
                        onItemClick = {
                            onNavigate(MainNavKey.BookDetail(loan.book.id))
                        },
                        onDeliverBookClick = {
                            onEvent(BorrowingEvent.OnBookDeliver(loan.id))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        alternate = index % 2 == 0
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.BorrowedSection(
    loans: List<Loan>,
    onEvent: (BorrowingEvent) -> Unit,
    onNavigate: (NavKey) -> Unit
) {
    val lazyListState = rememberLazyListState()

    ScrollbarContainer(
        adapter = rememberScrollbarAdapter(lazyListState),
        modifier = Modifier.weight(1f)
    ) {
        LazyColumn(
            modifier = Modifier
                .alignHorizontalSpace()
                .fillMaxHeight(),
            state = lazyListState,
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 32.dp,
                bottom = 32.dp
            )
        ) {
            if (loans.isEmpty()) {
                item {
                    Text("Belum ada buku yang dipinjam.")
                }
            } else {
                itemsIndexed(loans) { index, loan ->
                    BorrowedItem(
                        loan = loan,
                        onItemClick = {
                            onNavigate(MainNavKey.BookDetail(loan.book.id))
                        },
                        onReturnBookClick = {
                            onEvent(BorrowingEvent.OnBookReturn(loan.id))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        alternate = index % 2 == 0
                    )
                }
            }
        }
    }
}

@Composable
@Preview(widthDp = 1280, heightDp = 720)
private fun BorrowingScreenPreview() {
    AppTheme {
        BorrowingScreenContent(
            uiState = BorrowingUiState(),
            onEvent = {},
            onNavigate = {}
        )
    }
}
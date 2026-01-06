package com.perpushub.bandung.admin.ui.main.borrowing.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.perpushub.bandung.admin.common.model.LoanRequest
import com.perpushub.bandung.admin.common.model.LoanRequestStatus
import com.perpushub.bandung.admin.ui.main.common.component.BookCover
import com.perpushub.bandung.admin.ui.main.common.component.ExpanderItem
import com.perpushub.bandung.admin.ui.main.common.component.ItemRow
import com.perpushub.bandung.admin.ui.main.common.util.DateUtil
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.AccentButton
import io.github.composefluent.component.Button
import io.github.composefluent.component.Text

@Composable
fun RequestsItem(
    loanRequest: LoanRequest,
    onItemClick: () -> Unit,
    onApproveClick: () -> Unit,
    onRejectClick: () -> Unit,
    modifier: Modifier = Modifier,
    alternate: Boolean = false
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isAtLeastMediumBreakpoint =
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    Column(
        modifier = modifier
            .clip(FluentTheme.shapes.control)
            .let {
                if (alternate) {
                    it.background(
                        color = FluentTheme.colors.background.card.default,
                        shape = FluentTheme.shapes.control
                    )
                } else {
                    it
                }
            }
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        if (loanRequest.status == LoanRequestStatus.PENDING) {
            Text(
                text = "Sedang ditinjau",
                modifier = Modifier.align(Alignment.End),
                color = FluentTheme.colors.system.caution,
                style = FluentTheme.typography.bodyStrong
            )
        } else {
            Text(
                text = "Ditolak",
                modifier = Modifier.align(Alignment.End),
                color = FluentTheme.colors.system.critical,
                style = FluentTheme.typography.bodyStrong
            )
        }
        Spacer(Modifier.height(16.dp))
        Row {
            BookCover(
                coverUrl = loanRequest.book.coverUrl,
                contentDescription = loanRequest.book.title,
                width = 80.dp
            )
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = loanRequest.book.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = if (loanRequest.book.authors.isEmpty()) {
                        "Unknown"
                    } else {
                        loanRequest.book.authors.joinToString(", ") { it.name }
                    },
                    color = FluentTheme.colors.text.text.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = FluentTheme.typography.caption
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = loanRequest.book.description,
                    color = FluentTheme.colors.text.text.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    style = FluentTheme.typography.caption
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        ExpanderItem(
            title = "Rincian peminjaman"
        ) {
            ItemRow(
                items = listOf(
                    {
                        Text("Perpustakaan")
                    },
                    {
                        Text(loanRequest.library.name)
                    }
                )
            )
            ItemRow(
                items = listOf(
                    {
                        Text("Nama penerima")
                    },
                    {
                        Text(
                            text = loanRequest.recipientName,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                )
            )
            ItemRow(
                items = listOf(
                    {
                        Text("Nomor telepon")
                    },
                    {
                        Text(
                            text = loanRequest.phoneNumber,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                )
            )
            ItemRow(
                items = listOf(
                    {
                        Text("Alamat")
                    },
                    {
                        Text(
                            text = buildString {
                                append(loanRequest.addressLine)
                                append(", ")
                                append(loanRequest.city)
                                append(", ")
                                append(loanRequest.province)
                                append(" ")
                                append(loanRequest.postalCode)
                            },
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 3
                        )
                    }
                )
            )
            ItemRow(
                items = listOf(
                    {
                        Text("Tanggal pengembalian")
                    },
                    {
                        Text(
                            text = DateUtil.formatDate(loanRequest.dueDate),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                ),
                separatorVisible = false
            )
        }
        if (loanRequest.status == LoanRequestStatus.PENDING) {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AccentButton(
                    onClick = onApproveClick,
                    modifier = Modifier.let {
                        if (isAtLeastMediumBreakpoint) {
                            it
                        } else {
                            it.weight(1f)
                        }
                    }
                ) {
                    Text("Setujui")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onRejectClick,
                    modifier = Modifier.let {
                        if (isAtLeastMediumBreakpoint) {
                            it
                        } else {
                            it.weight(1f)
                        }
                    }
                ) {
                    Text("Tolak")
                }
            }
        }
    }
}
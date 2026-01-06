package com.perpushub.bandung.admin.ui.main.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.AccentButton
import io.github.composefluent.component.Button
import io.github.composefluent.component.DialogSize
import io.github.composefluent.component.FluentDialog
import io.github.composefluent.component.Text
import io.github.composefluent.component.TextField

@Composable
fun AddBookDialog(
    visible: Boolean,
    onAddClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var isbn by rememberSaveable(visible) { mutableStateOf("") }
    val width = 360.dp

    BoxWithConstraints {
        val maxDialogWidth = maxWidth - 16.dp

        FluentDialog(
            visible = visible,
            size = if (maxDialogWidth > width) {
                DialogSize(width, width)
            } else {
                DialogSize(maxDialogWidth, maxDialogWidth)
            }
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(FluentTheme.colors.background.layer.alt)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Tambah buku",
                        style = FluentTheme.typography.subtitle
                    )
                    Spacer(Modifier.height(12.dp))
                    TextField(
                        value = isbn,
                        onValueChange = {
                            isbn = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        header = {
                            Text("ISBN")
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .background(FluentTheme.colors.stroke.surface.default)
                )
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .padding(horizontal = 25.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AccentButton(
                            onClick = {
                                onAddClick(isbn)
                                onDismissRequest()
                            },
                            disabled = isbn.isBlank(),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Tambah")
                        }
                        Button(
                            onClick = onDismissRequest,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Batal")
                        }
                    }
                }
            }
        }
    }
}
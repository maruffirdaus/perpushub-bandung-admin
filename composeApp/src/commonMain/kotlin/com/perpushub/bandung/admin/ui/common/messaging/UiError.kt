package com.perpushub.bandung.admin.ui.common.messaging

data class UiError(
    override val message: String,
    override val title: String = "Terjadi kesalahan"
) : UiMessage

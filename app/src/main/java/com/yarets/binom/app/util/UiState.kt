package com.yarets.binom.app.util

sealed class UiState <out T:Any> {
    data object Default: UiState<Nothing>()
    data object Loading: UiState<Nothing>()
    data class Success<out T:Any>(val data: T): UiState<T>()
    data class Error(val e: Throwable): UiState<Nothing>()
}
package org.d3if3102.dicoding.utils

sealed class Result{
    data class Success<out T>(val data: T): Result()
    data class Error(val exception: Throwable): Result()
    data class Loading(val isLoading: Boolean): Result()
}

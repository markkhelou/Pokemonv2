package com.ouday.pokemon.core

/**
 * Worker represent the different status of an intense instructions
 * Used with any coroutine functions (API call, Database operations, IO ...)
 */
data class Worker<out T>(val status: Status, val data: T?, val message: String?) {

    var throwable: Throwable? = null

    constructor(status: Status, data: T?, message: String? = null, throwable: Throwable?) : this(
        status,
        data,
        message ?: ""
    ) {
        this.throwable = throwable
    }

    companion object {
        fun <T> success(data: T?): Worker<T> {
            return Worker(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): Worker<T> {
            return Worker(Status.ERROR, data, msg)
        }

        fun <T> error(throwable: Throwable?, data: T? = null): Worker<T> {
            return Worker(Status.ERROR, data, throwable = throwable)
        }

        fun <T> loading(): Worker<T> {
            return Worker(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    LOADING,
    ERROR,
    SUCCESS
}

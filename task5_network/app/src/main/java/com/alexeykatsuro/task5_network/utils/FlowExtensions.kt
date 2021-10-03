package com.alexeykatsuro.task5_network.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.filterWithPrevious(predicate: suspend (previous: T, last: T) -> Boolean): Flow<T> =
        flow {
            var lastState: T? = null
            collect { state ->
                if (lastState != null) {
                    if (predicate(lastState!!, state)) {
                        emit(state)
                    }
                }
                lastState = state
            }
        }
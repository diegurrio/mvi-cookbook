package com.diego.mvi.mvicore

import io.reactivex.Observable

/**
 * An implementation of the MviMiddleware interface needs to identify if a partial state
 * is meant to navigate away from the current view and handle the navigation or pass along
 * its input partial state.
 */
interface MviMiddleware<PS : MviPartialState<*>> {
    /**
     * Identify if the input partialState is meant to navigate away from the current view.
     */
    fun process(partialState: PS): Observable<PS> = Observable.just(partialState)
}
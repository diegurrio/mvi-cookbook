package com.diego.mvi.mvicore

/**
 * An implementation of MviPartialState represents a partial change in the view.
 * Its job is to grab that partial change and reduce it to a final ViewState.
 */
interface MviPartialState<VS : MviViewState> {
    /**
     * Combine the previous state with the changes applied by the class implementing this
     * interface
     */
    fun reduce(previousState: VS): VS
}
package com.diego.mvi.mvicore

import io.reactivex.Observable

/**
 * The View observes and depicts the model’s state. The view forwards each interaction as an Intent.
 * The View takes input from the ViewModel and emit back intents.
 */
interface MviView<in VS : MviViewState, I : MviIntent> {
    fun render(viewState: VS)
    fun emitIntents(): Observable<I>
}
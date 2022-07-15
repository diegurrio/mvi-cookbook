package com.diego.mvi.mvicore

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class MviPresenter<VS : MviViewState, V : MviView<VS, IN>, PS : MviPartialState<VS>, IN : MviIntent>(
    private val middleware: MviMiddleware<PS>
) : ViewModel() {

    protected abstract val defaultViewState: VS
    protected lateinit var view: V
    private var compositeDisposable = CompositeDisposable()
    private val stateSubject = BehaviorSubject.create<VS>()

    fun getViewState(): VS = stateSubject.value ?: defaultViewState

    internal fun attachView(view: V) {
        this.view = view
    }

    internal fun bind() {
        // TODO make sure that it's called once - use flag or something else
        subscribeState(Observable.merge(mapIntents(), mapPresenterActions()), middleware)
        renderStates()
    }

    @CallSuper
    internal fun unbind() {
        compositeDisposable.clear()
    }

    protected fun just(partialState: PS): Observable<PS> = Observable.just(partialState)

    @MainThread
    private fun renderStates() {
        compositeDisposable += stateSubject.distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ state ->
                view.render(state)
            }, {
                Log.e("TAG","renderStates error $it!")
            }, {
                Log.d("TAG","renderStates completed!")
            }) // TODO add error handling!
    }

    private fun subscribeState(intents: Observable<PS>, middleware: MviMiddleware<PS>) {
        compositeDisposable += intents

            // Navigation always performed on the main thread as ConnectionMiddleware.navController
            // had problems enforcing, preventing LifecycleRegistry.setCurrentState() from throwing
            // IllegalStateException.
            // https://eargodev.atlassian.net/browse/EMA3-4252?focusedCommentId=93619
            .observeOn(AndroidSchedulers.mainThread())

            .flatMap { middleware.process(it) }
            .scan(getViewState(), this::reduce)
            //            .replay(1) // TODO dig into this a bit
            //            .autoConnect(0)
            .subscribeBy(
                onNext = { stateSubject.onNext(it) },
                onError = {
                    stateSubject.onError(it)
                },
                onComplete = { stateSubject.onComplete() }
            )
    }

    private fun reduce(previousState: VS, partialState: PS): VS {
        return partialState.reduce(previousState)
    }

    private fun mapIntents(): Observable<PS> {
        return view.emitIntents().flatMap { intentToPartialState(it) }
    }

    private fun mapPresenterActions(): Observable<PS> {
        return presenterAction().flatMap { intentToPartialState(it) }
    }

    protected open fun presenterAction(): Observable<IN> {
        return PublishSubject.create()
    }

    abstract fun intentToPartialState(intent: IN): Observable<PS>
}
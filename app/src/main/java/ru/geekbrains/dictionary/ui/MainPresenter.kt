package ru.geekbrains.dictionary.ui

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.RepositoryImpl
import ru.geekbrains.dictionary.domain.DataSourceLocal
import ru.geekbrains.dictionary.domain.DataSourceRemote
import ru.geekbrains.dictionary.domain.SchedulerProvider

class MainPresenter<T : AppState, V : MainContracts.View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : MainContracts.Presenter<T, V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach(view: V) {
        this.view = null
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { view?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                view?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                view?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
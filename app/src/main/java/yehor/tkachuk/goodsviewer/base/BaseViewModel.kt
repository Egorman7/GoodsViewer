package yehor.tkachuk.goodsviewer.base

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import yehor.tkachuk.goodsviewer.utils.rx.ErrorHandler
import yehor.tkachuk.goodsviewer.utils.rx.SchedulersProvider

abstract class BaseViewModel : ViewModel(), KoinComponent{
    private val errorHandler by inject<ErrorHandler>()
    private val schedulersProvider by inject<SchedulersProvider>()
    private val disposables: CompositeDisposable = CompositeDisposable()

    protected fun <T>subscribe(single: Single<T>, onSuccess: (T) -> Unit,
                               onError: (Throwable) -> Unit = {errorHandler.handleError(it)}){
        disposables.add(single.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribe(onSuccess, onError))
    }

    protected fun complete(completable: Completable, onFinish: () -> Unit){
        disposables.add(completable.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main()).subscribe(onFinish, {
                errorHandler.handleError(it)
            }))
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
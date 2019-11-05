package yehor.tkachuk.goodsviewer.base

import androidx.lifecycle.ViewModel
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

    protected fun <T>subscribe(single: Single<T>, onSuccess: (T) -> Unit){
        disposables.add(single.subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribe(onSuccess, {errorHandler.handleError(it)}))
    }
}
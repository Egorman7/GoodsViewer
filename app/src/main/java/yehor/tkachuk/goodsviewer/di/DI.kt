package yehor.tkachuk.goodsviewer.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManagerImpl
import yehor.tkachuk.goodsviewer.utils.rx.ErrorHandler
import yehor.tkachuk.goodsviewer.utils.rx.ErrorHandlerImpl
import yehor.tkachuk.goodsviewer.utils.rx.SchedulersProvider
import yehor.tkachuk.goodsviewer.utils.rx.SchedulersProviderImpl
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

private val dataModule = module {
    single<MainDataManager>{ MainDataManagerImpl() }
}

private val apiModule = module {

}

private val utilModule = module {
    single<ErrorHandler>{ ErrorHandlerImpl(androidContext()) }
    single<SchedulersProvider> { SchedulersProviderImpl() }
}

val moduleList = listOf(viewModelModule, dataModule, apiModule, utilModule)
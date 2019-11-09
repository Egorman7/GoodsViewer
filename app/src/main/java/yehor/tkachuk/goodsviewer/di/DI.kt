package yehor.tkachuk.goodsviewer.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.data.GoodsDataManagerImpl
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManagerImpl
import yehor.tkachuk.goodsviewer.utils.ProfileManager
import yehor.tkachuk.goodsviewer.utils.ProfileManagerImpl
import yehor.tkachuk.goodsviewer.utils.UserManager
import yehor.tkachuk.goodsviewer.utils.UserManagerImpl
import yehor.tkachuk.goodsviewer.utils.rx.ErrorHandler
import yehor.tkachuk.goodsviewer.utils.rx.ErrorHandlerImpl
import yehor.tkachuk.goodsviewer.utils.rx.SchedulersProvider
import yehor.tkachuk.goodsviewer.utils.rx.SchedulersProviderImpl
import yehor.tkachuk.goodsviewer.viewmodel.ListFragmentViewModel
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel
private const val SHARED_PREFS = "goodsviewerprefs"

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { ListFragmentViewModel(get()) }
}

private val dataModule = module {
    single<MainDataManager>{ MainDataManagerImpl(get(), get()) }
    single<GoodsDataManager> { GoodsDataManagerImpl(get(), get()) }
}

private val apiModule = module {
    single<MainApi>{ Retrofit.Builder()
        .baseUrl(MainApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(MainApi::class.java)}
}

private val utilModule = module {
    single<ErrorHandler>{ ErrorHandlerImpl(androidContext()) }
    single<SchedulersProvider> { SchedulersProviderImpl() }
    single<UserManager> { UserManagerImpl(get()) }
    single<ProfileManager> { ProfileManagerImpl(androidContext(), get()) }
    single<SharedPreferences> { androidContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE) }
}

val moduleList = listOf(viewModelModule, dataModule, apiModule, utilModule)
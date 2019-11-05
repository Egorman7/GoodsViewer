package yehor.tkachuk.goodsviewer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import yehor.tkachuk.goodsviewer.di.moduleList

class GoodsViewerApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(moduleList)
            androidContext(applicationContext)
        }
    }
}
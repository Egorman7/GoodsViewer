package yehor.tkachuk.goodsviewer.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<T: ViewModel>(kclass: KClass<T>) : AppCompatActivity(){
    protected val viewModel by viewModel<T>(kclass)
}
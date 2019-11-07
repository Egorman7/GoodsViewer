package yehor.tkachuk.goodsviewer.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseFragment<T: ViewModel>(clazz: KClass<T>): Fragment(){
    protected val viewModel by viewModel<T>(clazz)
}
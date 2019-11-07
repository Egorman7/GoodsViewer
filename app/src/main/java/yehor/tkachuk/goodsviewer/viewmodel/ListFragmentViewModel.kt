package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel

class ListFragmentViewModel : BaseViewModel(){
    val listOfGoods = MutableLiveData<Unit>()

    fun loadList(forceLoad: Boolean = false){}
}
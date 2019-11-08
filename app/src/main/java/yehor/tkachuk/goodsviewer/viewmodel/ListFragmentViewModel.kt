package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.model.Good

class ListFragmentViewModel(private val dataManager: GoodsDataManager) : BaseViewModel(){
    val listOfGoods = MutableLiveData<List<Good>>()

    fun loadList(forceLoad: Boolean = false){
        subscribe(dataManager.loadList(forceLoad)){
            listOfGoods.value = it
        }
    }
}
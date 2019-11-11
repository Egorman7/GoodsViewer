package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.SaveResult
import yehor.tkachuk.goodsviewer.utils.SingleLiveEvent

class ListFragmentViewModel(private val dataManager: GoodsDataManager) : BaseViewModel(){
    val listOfGoods = MutableLiveData<List<Good>>()
    val saveResult = SingleLiveEvent<SaveResult>()

    fun loadList(forceLoad: Boolean = false){
        subscribe(dataManager.loadLocalList(),{})
        subscribe(dataManager.loadList(forceLoad),{
            listOfGoods.value = it
        }, {
            loadLocal()
        })
    }

    fun loadLocal(){
        subscribe(dataManager.loadLocalList(), {
            listOfGoods.value = it
        })
    }

    fun saveGood(good: Good){
        subscribe(dataManager.saveGood(good), {
            saveResult.callWithValue(it)
        })
    }
}
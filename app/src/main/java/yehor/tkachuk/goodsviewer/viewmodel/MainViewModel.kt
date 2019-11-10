package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.utils.SingleLiveEvent

class MainViewModel(private val dataManager: MainDataManager) : BaseViewModel(){
    private var listCollapsed = false
    val loggedIn = MutableLiveData<Boolean>()
    val expandList = SingleLiveEvent<Unit>()

    fun autoLogin(){
        subscribe(dataManager.autoLogin()){ logged ->
            loggedIn.value = logged
        }
    }

    fun setCollapsed(){
        listCollapsed = true
    }

    fun setExpanded(){
        listCollapsed = false
    }

    fun expandIfCollapsed(): Boolean{
        return listCollapsed.also {
            if(it){
                expandList.call()
            }
        }
    }
}
package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.model.Good

class MainViewModel(private val dataManager: MainDataManager) : BaseViewModel(){
    val loggedIn = MutableLiveData<Boolean>()

    fun autoLogin(){
        subscribe(dataManager.autoLogin()){ logged ->
            loggedIn.value = logged
        }
    }
}
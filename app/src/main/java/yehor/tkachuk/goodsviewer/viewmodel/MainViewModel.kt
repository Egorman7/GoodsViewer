package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.MainDataManager

class MainViewModel(private val dataManager: MainDataManager) : BaseViewModel(){
    val click = MutableLiveData<String>()
    fun test(){
        subscribe(dataManager.test()){
            click.value = "Yeah!"
        }
    }
}
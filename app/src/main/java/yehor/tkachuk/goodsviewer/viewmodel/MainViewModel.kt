package yehor.tkachuk.goodsviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import yehor.tkachuk.goodsviewer.base.BaseViewModel
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.model.Profile
import yehor.tkachuk.goodsviewer.utils.SingleLiveEvent

class MainViewModel(private val dataManager: MainDataManager) : BaseViewModel(){
    private var listCollapsed = false
    val loggedIn = MutableLiveData<AuthResult>().apply { value = AuthResult.empty() }
    val expandList = SingleLiveEvent<Unit>()
    val registerClicked = SingleLiveEvent<Unit>()
    val saved = SingleLiveEvent<Unit>()

    val profile = MutableLiveData<Profile>()

    fun autoLogin(){
        subscribe(dataManager.autoLogin(),{ logged ->
            loggedIn.value = logged
        })
    }

    fun login(username: String, password: String){
        subscribe(dataManager.performLogin(username, password),{
            loggedIn.value = it
        })
    }

    fun register(username: String, password: String){
        subscribe(dataManager.register(username, password),{
            loggedIn.value = it
        })
    }

    fun loadProfile(){
        subscribe(dataManager.loadProfile(),{
            profile.value = it
        })
    }

    fun saveImage(string: String){
        subscribe(dataManager.saveProfileImg(string),{
            profile.value = it
        })
    }

    fun saveProfile(name: String, surname: String){
        subscribe(dataManager.saveProfileData(name, surname),{
            profile.value = it
            loadProfile()
            saved.call()
        })
    }

    fun removeAvatar(){
        complete(dataManager.removeAvatar()){
            loadProfile()
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

    fun clickRegister(){
        registerClicked.call()
    }

    fun logOut() {
        complete(dataManager.logOut()){
            removeAvatar()
            loggedIn.value = AuthResult.empty()
        }
    }
}
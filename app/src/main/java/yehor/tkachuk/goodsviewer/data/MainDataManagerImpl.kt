package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.api.AuthRequest
import yehor.tkachuk.goodsviewer.utils.UserManager

class MainDataManagerImpl(private val api: MainApi, private val userManager: UserManager) : MainDataManager{

    override fun performLogin(username: String, password: String): Single<Boolean> {
        return api.login(AuthRequest(username, password)).map { auth ->
            if(auth.success) {
                userManager.saveLastLogin(username, password)
                userManager.saveToken(auth.token)
            }
            auth.success
        }
    }

    override fun autoLogin(): Single<Boolean> {
        val request = userManager.getLastLogin()
        return if(request != null){
            api.login(request).map { auth ->
                if(auth.success) {
                    userManager.saveToken(auth.token)
                }
                auth.success
            }
        } else Single.just(false)
    }
}
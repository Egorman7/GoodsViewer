package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Auth
import yehor.tkachuk.goodsviewer.model.Good

interface MainDataManager{
    fun performLogin(username: String, password: String): Single<Boolean>
    fun autoLogin(): Single<Boolean>
}
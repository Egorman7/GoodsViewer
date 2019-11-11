package yehor.tkachuk.goodsviewer.data

import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Auth
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.Profile

interface MainDataManager{
    fun performLogin(username: String, password: String): Single<AuthResult>
    fun autoLogin(): Single<AuthResult>
    fun register(username: String, password: String): Single<AuthResult>
    fun loadProfile(): Single<Profile>
    fun saveProfileImg(string: String): Single<Profile>
    fun saveProfileData(name: String, surname: String) : Single<Profile>
    fun removeAvatar(): Completable
    fun logOut(): Completable
}
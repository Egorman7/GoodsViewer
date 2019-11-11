package yehor.tkachuk.goodsviewer.data

import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.model.Profile
import yehor.tkachuk.goodsviewer.model.api.AuthRequest
import yehor.tkachuk.goodsviewer.utils.ProfileManager
import yehor.tkachuk.goodsviewer.utils.UserManager

class MainDataManagerImpl(private val api: MainApi, private val userManager: UserManager,
                          private val profileManager: ProfileManager) : MainDataManager{

    override fun performLogin(username: String, password: String): Single<AuthResult> {
        return api.login(AuthRequest(username, password)).map { auth ->
            if(auth.success) {
                userManager.saveLastLogin(username, password)
                userManager.saveToken(auth.token)
                AuthResult.loggedIn()
            } else {
                AuthResult.notLogged()
            }
        }
    }

    override fun autoLogin(): Single<AuthResult> {
        val request = userManager.getLastLogin()
        return if(request != null){
            api.login(request).map { auth ->
                if(auth.success) {
                    userManager.saveToken(auth.token)
                    AuthResult.loggedIn()
                } else {
                    AuthResult.notLogged()
                }
            }
        } else Single.just(AuthResult.empty())
    }

    override fun register(username: String, password: String): Single<AuthResult> {
        return api.register(AuthRequest(username, password)).map { auth ->
            if(auth.success){
                userManager.saveLastLogin(username, password)
                userManager.saveToken(auth.token)
                AuthResult.registered()
            } else {
                AuthResult.notRegistered()
            }
        }
    }

    override fun loadProfile(): Single<Profile> {
        return profileManager.getProfile()
    }

    override fun saveProfileImg(string: String): Single<Profile> {
        return profileManager.setAvatar(string)
    }

    override fun saveProfileData(name: String, surname: String): Single<Profile> {
        return profileManager.saveProfile(Profile(name, surname))
    }

    override fun removeAvatar(): Completable {
        return profileManager.removeAvatar()
    }

    override fun logOut(): Completable {
        return profileManager.clear().doOnComplete {
            userManager.clear()
        }
    }
}
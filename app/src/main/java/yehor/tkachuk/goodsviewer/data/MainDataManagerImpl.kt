package yehor.tkachuk.goodsviewer.data

import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.Profile
import yehor.tkachuk.goodsviewer.model.api.AuthRequest
import yehor.tkachuk.goodsviewer.utils.ProfileManager
import yehor.tkachuk.goodsviewer.utils.UserManager

class MainDataManagerImpl(private val api: MainApi, private val userManager: UserManager,
                          private val profileManager: ProfileManager) : MainDataManager{

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

    override fun register(username: String, password: String): Single<Boolean> {
        return api.register(AuthRequest(username, password)).map { auth ->
            if(auth.success){
                userManager.saveLastLogin(username, password)
                userManager.saveToken(auth.token)
            }
            auth.success
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
}
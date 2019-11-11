package yehor.tkachuk.goodsviewer.utils

import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Profile

interface ProfileManager {
    fun saveProfile(profile: Profile): Single<Profile>
    fun getProfile(): Single<Profile>
    fun setAvatar(string: String): Single<Profile>
    fun removeAvatar(): Completable
    fun clear(): Completable
}
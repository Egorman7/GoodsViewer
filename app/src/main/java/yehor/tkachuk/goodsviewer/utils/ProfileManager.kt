package yehor.tkachuk.goodsviewer.utils

import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Profile

interface ProfileManager {
    fun saveProfile(profile: Profile): Completable
    fun getProfile(): Single<Profile>
}
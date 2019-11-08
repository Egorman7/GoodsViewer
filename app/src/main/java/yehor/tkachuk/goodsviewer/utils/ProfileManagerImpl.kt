package yehor.tkachuk.goodsviewer.utils

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import yehor.tkachuk.goodsviewer.model.Profile
import java.io.File

class ProfileManagerImpl(private val context: Context, private val prefs: SharedPreferences): ProfileManager{
    companion object{
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val AVATAR_FILENAME = "avatar.jpg"
    }

    override fun saveProfile(profile: Profile): Completable {
        return Completable.fromCallable {
            if(profile.firstName.isNotBlank() && profile.lastName.isNotBlank()) {
                prefs.edit()
                    .putString(KEY_FIRST_NAME, profile.firstName)
                    .putString(KEY_LAST_NAME, profile.lastName)
                    .apply()
            }
            saveAvatar(profile.avatar)
        }
    }

    override fun getProfile(): Single<Profile> {
        return Single.create { emitter ->
            val name = prefs.getString(KEY_FIRST_NAME, "") ?: ""
            val surname = prefs.getString(KEY_LAST_NAME, "") ?: ""
            val file = getFilePath()
            emitter.onSuccess(Profile(name, surname, file))
        }
    }

    fun getFilePath(): String{
        return File(context.filesDir, AVATAR_FILENAME).absolutePath
    }

    private fun saveAvatar(path: String){
        if(path.isNotBlank()) {
            val imageToSave = File(path)
            val avatar = File(context.filesDir, AVATAR_FILENAME)
            if(avatar.exists()){
                avatar.delete()
            }
            imageToSave.copyTo(avatar, true)
        }
    }
}
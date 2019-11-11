package yehor.tkachuk.goodsviewer.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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

    override fun saveProfile(profile: Profile): Single<Profile> {
        return Single.create { emitter ->
            if(profile.firstName.isNotBlank() && profile.lastName.isNotBlank()) {
                prefs.edit()
                    .putString(KEY_FIRST_NAME, profile.firstName)
                    .putString(KEY_LAST_NAME, profile.lastName)
                    .apply()
            }
            emitter.onSuccess(profile.copy(avatar = getFilePath()))
//            saveAvatar(profile.avatar)
        }
    }

    override fun setAvatar(string: String): Single<Profile> {
        return Single.create { emitter ->
            val bm = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(string))
            saveAvatar(bm)
            val name = prefs.getString(KEY_FIRST_NAME, "") ?: ""
            val surname = prefs.getString(KEY_LAST_NAME, "") ?: ""
            val file = getFilePath()
            emitter.onSuccess(Profile(name, surname, file))
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

    override fun removeAvatar(): Completable {
        return Completable.fromCallable {
            val f = File(getFilePath())
            if(f.exists()){
                f.delete()
            }
        }
    }

    override fun clear(): Completable {
        return Completable.fromCallable {
            prefs.edit()
                .remove(KEY_FIRST_NAME)
                .remove(KEY_LAST_NAME)
                .apply()
        }
    }

    private fun getFilePath(): String{
        return File(context.filesDir, AVATAR_FILENAME).absolutePath
    }

    private fun saveAvatar(bm: Bitmap){
        val avatar = File(context.filesDir, AVATAR_FILENAME)
        if(avatar.exists()){
            avatar.delete()
        }
        val stream = avatar.outputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    }
}
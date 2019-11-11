package yehor.tkachuk.goodsviewer.utils

import android.content.SharedPreferences
import yehor.tkachuk.goodsviewer.model.api.AuthRequest

class UserManagerImpl(private val prefs: SharedPreferences): UserManager {
    companion object{
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"

        private const val TOKEN_FORMAT = "Token %s"
    }
    private var token = ""
    override fun clear() {
        token = ""
        prefs.edit()
            .remove(KEY_PASSWORD)
            .remove(KEY_USERNAME)
            .apply()
    }

    override fun saveLastLogin(username: String, password: String) {
        prefs.edit()
            .putString(KEY_USERNAME,username)
            .putString(KEY_PASSWORD,password)
            .apply()
    }

    override fun getLastLogin(): AuthRequest? {
        return if(prefs.contains(KEY_USERNAME) && prefs.contains(KEY_PASSWORD)){
            AuthRequest(prefs.getString(KEY_USERNAME, "") ?: "",
                prefs.getString(KEY_PASSWORD, "") ?: "")
        } else null
    }

    override fun saveToken(token: String) {
        this.token = token
    }

    override fun getToken(): String {
        return TOKEN_FORMAT.format(token)
    }
}
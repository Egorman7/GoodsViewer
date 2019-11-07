package yehor.tkachuk.goodsviewer.utils

import yehor.tkachuk.goodsviewer.model.Auth
import yehor.tkachuk.goodsviewer.model.api.AuthRequest

interface UserManager {
    fun clear()
    fun saveLastLogin(username: String, password: String)
    fun getLastLogin(): AuthRequest?
    fun saveToken(token: String)
    fun getToken(): String
}
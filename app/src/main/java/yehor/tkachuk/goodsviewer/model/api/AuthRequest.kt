package yehor.tkachuk.goodsviewer.model.api

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
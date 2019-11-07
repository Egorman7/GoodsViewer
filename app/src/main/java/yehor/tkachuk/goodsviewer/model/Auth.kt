package yehor.tkachuk.goodsviewer.model

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("success") val success: Boolean,
    @SerializedName("token") val token: String
)
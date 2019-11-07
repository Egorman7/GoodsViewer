package yehor.tkachuk.goodsviewer.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("product") val productId: Int,
    @SerializedName("created_by") val author: User,
    @SerializedName("rate") val rate: Int,
    @SerializedName("text") val text: String
)
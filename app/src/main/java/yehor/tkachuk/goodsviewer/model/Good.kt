package yehor.tkachuk.goodsviewer.model

import com.google.gson.annotations.SerializedName

data class Good(
    @SerializedName("id") val id: Int,
    @SerializedName("img") val img: String,
    @SerializedName("text") val text: String,
    @SerializedName("title") val title: String
)
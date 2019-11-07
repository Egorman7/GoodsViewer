package yehor.tkachuk.goodsviewer.model.api

import com.google.gson.annotations.SerializedName

data class PostCommentRequest(
    @SerializedName("rate") val rate: Int,
    @SerializedName("text") val text: String
)
package yehor.tkachuk.goodsviewer.model.api

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("review_id") val reviewId: Int = -1
)
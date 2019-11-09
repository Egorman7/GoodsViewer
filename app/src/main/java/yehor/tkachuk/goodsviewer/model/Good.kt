package yehor.tkachuk.goodsviewer.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import yehor.tkachuk.goodsviewer.api.MainApi
import java.net.URL

data class Good(
    @SerializedName("id") val id: Int,
    @SerializedName("img") val img: String,
    @SerializedName("text") val text: String,
    @SerializedName("title") val title: String
) {
    fun getImageUrl(): Uri{
        return Uri.parse(MainApi.IMAGES_URL + img)
    }
}
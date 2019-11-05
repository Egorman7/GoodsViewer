package yehor.tkachuk.goodsviewer.api

import retrofit2.http.POST

interface MainApi {
    companion object{
        const val BASE_URL = ""
    }

    @POST("api/register")
    fun register()
}
package yehor.tkachuk.goodsviewer.api
import io.reactivex.Single
import retrofit2.http.*
import yehor.tkachuk.goodsviewer.model.Auth
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.api.AuthRequest
import yehor.tkachuk.goodsviewer.model.api.CommentResponse
import yehor.tkachuk.goodsviewer.model.api.PostCommentRequest

interface MainApi {
    companion object{
        const val BASE_URL = "http://smktesting.herokuapp.com/"
        const val IMAGES_URL = "http://smktesting.herokuapp.com/static/"
    }

    @POST("api/register/")
    fun register(@Body body: AuthRequest): Single<Auth>

    @POST("api/login/")
    fun login(@Body body: AuthRequest): Single<Auth>

    @GET("api/products/")

    fun getGoods(): Single<List<Good>>

    @POST("api/reviews/{product_id}")
    fun postComment(@Header("Authorization") token: String, @Path("product_id") productId: Int, @Body body: PostCommentRequest): Single<CommentResponse>

    @GET("api/reviews/{product_id}")
    fun getComments(@Path("product_id") productId: Int): Single<List<Comment>>
}
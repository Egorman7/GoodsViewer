package yehor.tkachuk.goodsviewer.data

import io.reactivex.Single
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.SaveResult
import yehor.tkachuk.goodsviewer.model.api.CommentResponse
import yehor.tkachuk.goodsviewer.model.api.PostCommentRequest
import yehor.tkachuk.goodsviewer.utils.LocalDataManager
import yehor.tkachuk.goodsviewer.utils.UserManager

class GoodsDataManagerImpl(private val api: MainApi, private val userManager: UserManager,
                           private val localDataManager: LocalDataManager): GoodsDataManager{
    private var loadedList = listOf<Good>()

    override fun loadList(force: Boolean): Single<List<Good>> {
        return if(force || loadedList.isEmpty()){
            api.getGoods().doAfterSuccess {
                loadedList = it
            }
        } else {
            Single.just(loadedList)
        }
    }

    override fun loadComments(productId: Int): Single<List<Comment>> {
        return api.getComments(productId)
    }

    override fun postComment(productId: Int, text: String, rate: Int): Single<CommentResponse> {
        return api.postComment(userManager.getToken(), productId, PostCommentRequest(
            rate, text
        ))
    }

    override fun loadLocalList(): Single<List<Good>> {
        return localDataManager.getLocalList()
    }

    override fun saveGood(good: Good): Single<SaveResult> {
        return localDataManager.save(good)
    }
}
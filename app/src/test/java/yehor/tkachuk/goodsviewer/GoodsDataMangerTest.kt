package yehor.tkachuk.goodsviewer

import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.data.GoodsDataManagerImpl
import yehor.tkachuk.goodsviewer.model.Comment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.model.User
import yehor.tkachuk.goodsviewer.model.api.CommentResponse
import yehor.tkachuk.goodsviewer.model.api.PostCommentRequest
import yehor.tkachuk.goodsviewer.utils.LocalDataManager
import yehor.tkachuk.goodsviewer.utils.UserManager

class GoodsDataMangerTest {

    @Mock
    lateinit var api: MainApi

    @Mock
    lateinit var userManager: UserManager

    @Mock
    lateinit var localDataManager: LocalDataManager

    lateinit var goodsDataManager: GoodsDataManager

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        goodsDataManager = GoodsDataManagerImpl(api, userManager, localDataManager)
    }

    @Test
    fun getListTest(){
        val list = listOf(Good(1, "", "", ""))
        Mockito.`when`(api.getGoods()).thenReturn(Single.just(list))
        val gotList = goodsDataManager.loadList(true).blockingGet()
        Mockito.verify(api).getGoods()
        assert(list.size == gotList.size)
    }

    @Test
    fun getCommentsTest(){
        val comments = listOf(Comment(1, 1, User(1, ""), 2, ""))
        Mockito.`when`(api.getComments(Mockito.anyInt())).thenReturn(Single.just(comments))
        val gotComments = goodsDataManager.loadComments(Mockito.anyInt()).blockingGet()
        Mockito.verify(api).getComments(Mockito.anyInt())
        assert(comments.size == gotComments.size)
    }

    @Test
    fun postCommentTest(){
        val text = "text"
        val rate = 2
        val postComment = PostCommentRequest(rate, text)
        val token = "token"
        val product = 1
        Mockito.`when`(api.postComment(token, product, postComment)).thenReturn(Single.just(
            CommentResponse(1)
        ))
        Mockito.`when`(userManager.getToken()).thenReturn(token)
        val post = goodsDataManager.postComment(product, text, rate).blockingGet()
        Mockito.verify(api).postComment(token, product, postComment)
        assert(post.reviewId != -1)
    }

    @After
    fun after(){
    }
}
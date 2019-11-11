package yehor.tkachuk.goodsviewer

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import yehor.tkachuk.goodsviewer.api.MainApi
import yehor.tkachuk.goodsviewer.data.GoodsDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManager
import yehor.tkachuk.goodsviewer.data.MainDataManagerImpl
import yehor.tkachuk.goodsviewer.model.Auth
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.model.api.AuthRequest
import yehor.tkachuk.goodsviewer.utils.LocalDataManager
import yehor.tkachuk.goodsviewer.utils.ProfileManager
import yehor.tkachuk.goodsviewer.utils.UserManager

class MainDataManagerTest {
    @Mock
    lateinit var api: MainApi

    @Mock
    lateinit var userManager: UserManager

    @Mock
    lateinit var profileManager: ProfileManager

    lateinit var mainDataManger: MainDataManager

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        mainDataManger = MainDataManagerImpl(api, userManager, profileManager)
    }

    @Test
    fun loginEmptyTest(){
        Mockito.`when`(userManager.getLastLogin()).thenReturn(null)
        val result = mainDataManger.autoLogin().blockingGet()
        Mockito.verify(userManager).getLastLogin()
        assert(result.state == AuthResult.State.EMPTY)
    }

    @Test
    fun loginAutoTest(){
        val username = "username"
        val password = "password"
        val request = AuthRequest(username, password)
        val token = "token"
        Mockito.`when`(api.login(request)).thenReturn(Single.just(Auth(true, token)))
        Mockito.`when`(userManager.getLastLogin()).thenReturn(request)
        val result = mainDataManger.autoLogin().blockingGet()
        Mockito.verify(userManager).getLastLogin()
        Mockito.verify(api).login(request)
        assert(result.state == AuthResult.State.LOGIN)
        assert(result.success)
    }

    @Test
    fun loginSuccessTest(){
        val username = "username"
        val password = "password"
        val request = AuthRequest(username, password)
        val token = "token"
        Mockito.`when`(api.login(request)).thenReturn(Single.just(Auth(false, token)))
        val result = mainDataManger.performLogin(username, password).blockingGet()
        Mockito.verify(api).login(request)
        assert(result.state == AuthResult.State.LOGIN)
        assert(!result.success)
    }

    @Test
    fun loginErrorTest(){
        val username = "username"
        val password = "password"
        val request = AuthRequest(username, password)
        val token = "token"
        Mockito.`when`(api.login(request)).thenReturn(Single.just(Auth(true, token)))
        val result = mainDataManger.performLogin(username, password).blockingGet()
        Mockito.verify(api).login(request)
        assert(result.state == AuthResult.State.LOGIN)
        assert(result.success)
    }
}
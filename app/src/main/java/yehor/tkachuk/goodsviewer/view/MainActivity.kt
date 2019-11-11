package yehor.tkachuk.goodsviewer.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_logout.view.*
import kotlinx.android.synthetic.main.drawer_list.view.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseActivity
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.utils.showDialog
import yehor.tkachuk.goodsviewer.utils.toast
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    companion object{
        private const val EXIT_INTERVAL = 2000L
    }
    private var lastPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpDrawer()
        viewModel.autoLogin()
        viewModel.loadProfile()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, ListFragment(), ListFragment::class.java.simpleName)
            .commit()

        toolbar_button_drawer.setOnClickListener {
            main_drawer_layout.openDrawer(GravityCompat.END)
        }

        viewModel.registerClicked.observe(this, Observer {
            setFragment(FragmentRegister())
        })

        viewModel.saved.observe(this, Observer {
            setFragment(ListFragment())
        })

        viewModel.loggedIn.observe(this, Observer { result ->
            when(result.state){
                AuthResult.State.EMPTY -> {loggedOut()}
                AuthResult.State.REGISTER -> {
                    if(result.success){
                        toast(getString(R.string.toast_register_success))
                        loggedIn()
                    } else {
                        loggedOut()
                        toast(getString(R.string.toast_register_error))
                    }
                }
                AuthResult.State.LOGIN -> {
                    if(result.success){
                        toast(getString(R.string.toast_login_success))
                        setFragment(ListFragment())
                        loggedIn()
                    } else {
                        loggedOut()
                        toast(getString(R.string.toast_login_error))
                    }
                }
            }
        })

        viewModel.profile.observe(this, Observer { profile ->
            main_drawer.drawer_profile_name.text = "%s\n%s".format(profile.firstName, profile.lastName)
            Glide.with(this)
                .load(profile.avatar)
                .error(R.drawable.ic_account_no_ava)
                .placeholder(R.drawable.ic_account_no_ava)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .circleCrop()
                .into(main_drawer.drawer_profile_img)
        })
    }

    private fun setUpDrawer(){
        main_drawer.drawer_list.setOnClickListener {
            handleDrawerClick(R.id.drawer_list)
        }
        main_drawer.drawer_login.setOnClickListener {
            handleDrawerClick(R.id.drawer_login)
        }
        main_drawer.drawer_logout.setOnClickListener {
            handleDrawerClick(R.id.drawer_logout)
        }
        main_drawer.drawer_profile.setOnClickListener {
            handleDrawerClick(R.id.drawer_profile)
        }
    }

    override fun onBackPressed() {
        if(main_drawer_layout.isDrawerOpen(GravityCompat.END)){
            main_drawer_layout.closeDrawer(GravityCompat.END)
            return
        }
        if(!viewModel.expandIfCollapsed()) {
            if(lastPressTime + EXIT_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
            } else {
                toast(getString(R.string.toast_exit))
                lastPressTime = System.currentTimeMillis()
            }
        }
    }

    private fun handleDrawerClick(id: Int){
        when(id){
            R.id.drawer_list -> {
                setFragment(ListFragment())
            }
            R.id.drawer_login -> {
                setFragment(FragmentLogin())
            }
            R.id.drawer_logout -> {
                showLogoutDialog()
            }
            R.id.drawer_profile -> {
                setFragment(ProfileFragment())
            }
        }
        main_drawer_layout.closeDrawer(GravityCompat.END)
    }

    private fun showLogoutDialog(){
        showDialog(R.layout.dialog_logout,{ dialog, view ->
            view.dialog_logout_cancel.setOnClickListener {
                dialog.dismiss()
            }
            view.dialog_logout_ok.setOnClickListener {
                viewModel.logOut()
                dialog.dismiss()
            }
        })
    }

    private fun loggedIn(){
        setFragment(ListFragment())
        main_drawer.drawer_login.visibility = View.GONE
        main_drawer.drawer_logout.visibility = View.VISIBLE
        main_drawer.drawer_profile.visibility = View.VISIBLE
    }

    private fun loggedOut(){
        main_drawer.drawer_login.visibility = View.VISIBLE
        main_drawer.drawer_logout.visibility = View.GONE
        main_drawer.drawer_profile.visibility = View.GONE
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment, fragment::class.java.simpleName)
            .commit()
    }
}

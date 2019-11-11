package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_list.view.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseActivity
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

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

        viewModel.loggedIn.observe(this, Observer { success ->
            if(success){
                setFragment(ListFragment())
                loggedIn()
            } else {
                loggedOut()
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

        //TODO comments, bugfix, local saving, logging out
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
        if(!viewModel.expandIfCollapsed()) {
            super.onBackPressed()
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

    }

    private fun loggedIn(){
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

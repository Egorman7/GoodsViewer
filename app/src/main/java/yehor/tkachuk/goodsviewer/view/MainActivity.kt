package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseActivity
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.autoLogin()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, ListFragment(), ListFragment::class.java.simpleName)
            .commit()

        toolbar_button_drawer.setOnClickListener {
            main_drawer_layout.openDrawer(GravityCompat.END)
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment, fragment::class.java.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if(!viewModel.expandIfCollapsed()) {
            super.onBackPressed()
        }
    }
}

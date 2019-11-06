package yehor.tkachuk.goodsviewer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseActivity
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

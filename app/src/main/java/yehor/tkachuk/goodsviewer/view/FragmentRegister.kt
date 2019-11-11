package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class FragmentRegister : BaseFragment<MainViewModel>(MainViewModel::class){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_title.text = getString(R.string.register_title)
        login_button_register.visibility = View.GONE
        login_button_enter.text = getString(R.string.register_button)
        login_button_enter.setOnClickListener {
            val username = login_input_username.text.toString()
            val password = login_input_password.text.toString()
            sharedViewModel.register(username, password)
        }
    }
}
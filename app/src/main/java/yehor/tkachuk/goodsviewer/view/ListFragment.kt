package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_good_list.*
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.viewmodel.ListFragmentViewModel

class ListFragment : BaseFragment<ListFragmentViewModel>(ListFragmentViewModel::class){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_good_list, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadList()
        viewModel.listOfGoods.observe(viewLifecycleOwner, Observer {
            list_swipe_refresh.isRefreshing = false
        })

        list_swipe_refresh.setOnRefreshListener {
            loadList(true)
        }
    }

    private fun loadList(force: Boolean = false){
        list_swipe_refresh.isRefreshing = true
        viewModel.loadList(force)
    }
}
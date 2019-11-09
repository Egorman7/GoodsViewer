package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_good_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.utils.collapse
import yehor.tkachuk.goodsviewer.view.adapter.GoodsListAdapter
import yehor.tkachuk.goodsviewer.viewmodel.ListFragmentViewModel
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class ListFragment : BaseFragment<MainViewModel>(MainViewModel::class){
    private val viewModel by viewModel<ListFragmentViewModel>()
    private val adapter by lazy { GoodsListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_good_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        list_recycler.adapter = adapter
        loadList()
        viewModel.listOfGoods.observe(viewLifecycleOwner, Observer {
            list_swipe_refresh.isRefreshing = false
            adapter.setData(it)
        })

        list_swipe_refresh.setOnRefreshListener {
            loadList(true)
        }

        adapter.onItemClicked = {good, pos ->
            if(!adapter.isCollapsed()){
                adapter.collapse()
            }
            list_swipe_refresh.collapse(300)
            //TODO finish this (expanding)
        }
    }

    private fun loadList(force: Boolean = false){
        list_swipe_refresh.isRefreshing = true
        viewModel.loadList(force)
    }
}
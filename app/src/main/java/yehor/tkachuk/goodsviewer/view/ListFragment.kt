package yehor.tkachuk.goodsviewer.view

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_good_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.utils.collapse
import yehor.tkachuk.goodsviewer.utils.expand
import yehor.tkachuk.goodsviewer.view.adapter.DetailPagerAdapter
import yehor.tkachuk.goodsviewer.view.adapter.GoodsListAdapter
import yehor.tkachuk.goodsviewer.viewmodel.ListFragmentViewModel
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class ListFragment : BaseFragment<MainViewModel>(MainViewModel::class){
    private val viewModel by viewModel<ListFragmentViewModel>()
    private val adapter by lazy { GoodsListAdapter() }
    private val pagerAdaper by lazy{ DetailPagerAdapter(childFragmentManager, listOf(
        getString(R.string.page_title_detail), getString(R.string.page_title_comments)
    ))}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_good_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        list_recycler.adapter = adapter
        list_detail_tabs.setupWithViewPager(list_detail_pager)
        list_detail_pager.adapter = pagerAdaper
        loadList()
        viewModel.listOfGoods.observe(viewLifecycleOwner, Observer {
            list_swipe_refresh.isRefreshing = false
            adapter.setData(it)
        })

        sharedViewModel.expandList.observe(viewLifecycleOwner, Observer {
            hideDetailContainer()
        })

        list_swipe_refresh.setOnRefreshListener {
            loadList(true)
        }

        adapter.onItemClicked = {good, pos ->
            if(!adapter.isCollapsed()){
                list_swipe_refresh.collapse(300){
                    adapter.collapse()
                }
            }
            Log.d("LIST", "${good.id}")
            loadDetailContainer(good)
        }
    }

    private fun loadList(force: Boolean = false){
        list_swipe_refresh.isRefreshing = true
        viewModel.loadList(force)
    }

    private fun loadDetailContainer(good: Good){
        list_detail_container.visibility = View.VISIBLE
        list_detail_container.alpha = 1f
//        list_detail_container.animate()
//            .alpha(1f)
//            .setDuration(300)
//            .start()
        pagerAdaper.setFragments(listOf(
            ListDetailFragment.createInstance(good),
            ListCommentsFragment.createInstance(good)
        ))
        sharedViewModel.setCollapsed()
    }

    private fun hideDetailContainer(){
        list_swipe_refresh.expand(300)
        list_detail_container.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationEnd(p0: Animator?) {
                    pagerAdaper.clear()
                    list_detail_container.visibility = View.GONE
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {}
            })
        if (adapter.isCollapsed()) {
            adapter.expand()
            sharedViewModel.setExpanded()
        }
    }
}
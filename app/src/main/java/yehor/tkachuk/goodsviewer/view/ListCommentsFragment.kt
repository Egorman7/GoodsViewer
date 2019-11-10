package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_good_comments.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.data.CommentsDataManager
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.view.adapter.CommentsAdapter
import yehor.tkachuk.goodsviewer.viewmodel.CommentsViewModel
import yehor.tkachuk.goodsviewer.viewmodel.MainViewModel

class ListCommentsFragment : BaseFragment<MainViewModel>(MainViewModel::class){
    companion object{
        private const val KEY_GOOD_ID = "id"
        fun createInstance(good: Good): ListCommentsFragment{
            return ListCommentsFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(KEY_GOOD_ID, good.id)
                }
            }
        }
    }
    private val viewModel by viewModel<CommentsViewModel>()

    private val adapter by lazy { CommentsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        good_comments_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        good_comments_recycler.adapter = adapter
        arguments?.also { args ->
            viewModel.loadComments(args.getInt(KEY_GOOD_ID))
        }
        viewModel.commentList.observe(viewLifecycleOwner, Observer {
            adapter.setComments(it)
        })
    }
}
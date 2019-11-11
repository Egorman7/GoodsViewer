package yehor.tkachuk.goodsviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_comment.view.*
import kotlinx.android.synthetic.main.fragment_good_comments.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import yehor.tkachuk.goodsviewer.R
import yehor.tkachuk.goodsviewer.base.BaseFragment
import yehor.tkachuk.goodsviewer.model.AuthResult
import yehor.tkachuk.goodsviewer.model.Good
import yehor.tkachuk.goodsviewer.utils.showDialog
import yehor.tkachuk.goodsviewer.utils.toast
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
    private var productId: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_good_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        good_comments_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        good_comments_recycler.adapter = adapter
        arguments?.also { args ->
            viewModel.loadComments(args.getInt(KEY_GOOD_ID).also {
                productId = it
            })
        }
        viewModel.commentList.observe(viewLifecycleOwner, Observer {
            adapter.setComments(it)
        })
        viewModel.commentPosted.observe(viewLifecycleOwner, Observer {
            context?.toast(getString(R.string.toast_comment_posted))
        })
        sharedViewModel.loggedIn.observe(viewLifecycleOwner, Observer {
            if(it.state == AuthResult.State.EMPTY || !it.success){
                good_comments_button_add.hide()
            } else {
                good_comments_button_add.show()
            }
        })
        good_comments_button_add.setOnClickListener {
            context?.showDialog(R.layout.dialog_comment, {dialog, v ->
                v.dialog_comment_cancel.setOnClickListener {
                    dialog.dismiss()
                }
                v.dialog_comment_ok.setOnClickListener {
                    val rate = v.dialog_comment_rating.rating.toInt()
                    val text = v.dialog_comment_input.text.toString()
                    viewModel.postComment(productId, text, rate)
                    dialog.dismiss()
                }
            })
        }
    }
}
package com.dohyun.searchimgapp.view.search

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.data.entity.ImageInfo
import com.dohyun.searchimgapp.databinding.FragmentSearchBinding
import com.dohyun.searchimgapp.view.base.BaseFragment
import com.dohyun.searchimgapp.view.util.toVisibility
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by activityViewModels<SearchViewModel>()
    private val adapter = ImageAdapter()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {

        adapter.apply { onClick = this@SearchFragment::gotoFullScreen }
        requireDataBinding().resultList.adapter = adapter

        requireDataBinding().searchEdit.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    requireDataBinding().searchEdit.addTextChangedListener(textWatcher)
                } else {
                    requireDataBinding().searchEdit.removeTextChangedListener(textWatcher)
                }
            }
        })


        observeData()

        requireDataBinding().resultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!requireDataBinding().resultList.canScrollVertically(1)) {
                    viewModel.next(requireDataBinding().searchEdit.text.toString().trim())
                }
            }
        })
    }

    private fun observeData() {
        with(viewModel) {
            result.observe(viewLifecycleOwner) {
                if (it == null || it.metaData.totalCount == 0) showToast("검색 결과가 없습니다.")
                else adapter.updateLists(it.documents)
            }

            entireProgressVisible.observe(viewLifecycleOwner) { visible ->
                requireDataBinding().searchEntireProgressBar.visibility = visible.toVisibility()
            }

            bottomProgressVisible.observe(viewLifecycleOwner) { visible ->
                requireDataBinding().searchBottomProgressBar.visibility = visible.toVisibility()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null && !s.toString().equals("")) {
                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        viewModel.searchImg(s.toString().trim())
                    }
                }, 1000)
            }
        }
    }

    private fun gotoFullScreen(info: ImageInfo) {
        val item = arrayListOf<ImageInfo>(info)
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(item.toTypedArray())
        requireView().findNavController().navigate(action)
    }

}
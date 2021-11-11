package com.dohyun.searchimgapp.view.search

import android.os.Bundle
import android.os.Parcelable
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
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!viewModel.entireProgressVisible.value!!) {
                    if (!requireDataBinding().resultList.canScrollVertically(1)) {
                        viewModel.next(requireDataBinding().searchEdit.text.toString().trim())
                    }
                }
            }
        })
    }

    private var mLayoutManagerSavedState : Parcelable? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("position", requireDataBinding().resultList.layoutManager?.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("position")
        }
    }

    override fun onStart() {
        super.onStart()
        if (mLayoutManagerSavedState != null) {
            requireDataBinding().resultList.layoutManager?.onRestoreInstanceState(mLayoutManagerSavedState)
        }
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

            errorMsg.observe(viewLifecycleOwner) {
                showToast(it)
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        var timer = Timer()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    if(s != null && !s.toString().equals("")) {
                        viewModel.searchImg(s.toString().trim())
                    }
                }
            }, 1000L)
        }
    }

    private fun gotoFullScreen(info: ImageInfo) {
        val item = arrayListOf<ImageInfo>(info)
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(item.toTypedArray())
        requireView().findNavController().navigate(action)
    }

}
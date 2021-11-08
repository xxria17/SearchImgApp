package com.dohyun.searchimgapp.view.search

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.data.entity.ImageInfo
import com.dohyun.searchimgapp.databinding.FragmentSearchBinding
import com.dohyun.searchimgapp.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        val adapter = ImageAdapter()
            .apply { onClick = this@SearchFragment::gotoFullScreen }
        requireDataBinding().resultList.adapter = adapter

        requireDataBinding().searchEdit.addTextChangedListener(textWatcher)

        viewModel.result.observe(viewLifecycleOwner){
            if (it.isSuccessful) {
                if (it.body()!!.metaData.totalCount == 0) showToast("검색 결과가 없습니다.")
                else adapter.updateLists(it.body()!!.documents)
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
                        viewModel.searchImg(s.toString())
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
package com.dohyun.searchimgapp.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.databinding.FragmentDetailBinding
import com.dohyun.searchimgapp.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        setUI()

        requireDataBinding().detailBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUI() {
        val args : DetailFragmentArgs by navArgs()
        val item = args.value.toCollection(ArrayList())[0]

        Glide.with(requireContext())
                .load(item.imageUrl)
                .into(requireDataBinding().detailImg)

        requireDataBinding().datetimeTv.text = convertDate(item.datetime)
        requireDataBinding().sitenameTv.text = item.siteName
    }

    private fun convertDate(datetime: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val date = format.parse(datetime)
        val convert = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss")
        return convert.format(date)
    }

}
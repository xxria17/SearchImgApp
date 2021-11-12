package com.dohyun.searchimgapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.data.entity.ImageInfo
import com.dohyun.searchimgapp.databinding.FragmentDetailBinding
import com.dohyun.searchimgapp.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val args : DetailFragmentArgs by navArgs()
    private var data : ImageInfo? = null

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun init() {
        val item = args.value.toCollection(ArrayList())[0]
        setUI(item)

        requireDataBinding().detailBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("data", args.value[0])
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable<ImageInfo>("data")
        }
    }

    override fun onStart() {
        super.onStart()
        data?.let { setUI(it) }
    }

    private fun setUI(item: ImageInfo) {
        Glide.with(requireContext())
            .load(item.imageUrl)
            .thumbnail(Glide.with(requireContext()).load(item.thumbnameUrl).fitCenter())
            .error(R.drawable.photo)
            .fallback(R.drawable.photo)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
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
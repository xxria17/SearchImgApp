package com.dohyun.searchimgapp.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dohyun.searchimgapp.R
import com.dohyun.searchimgapp.data.entity.ImageInfo

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val list = mutableListOf<ImageInfo>()
    var onClick: (ImageInfo) -> Unit = {}

    fun updateLists(images: List<ImageInfo>) {
        this.list.clear()
        this.list.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val result = list[position]
        holder.bind(result)
        holder.itemView.setOnClickListener { onClick(result) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ImageViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        private val img : ImageView = view!!.findViewById(R.id.item_img)
        private val size = getDeviceSize(view!!.context)
        fun bind(info : ImageInfo) {
            Glide.with(itemView)
                .load(info.thumbnameUrl)
                .override(size)
                .centerCrop()
                .into(img)
        }
    }

    private fun getDeviceSize(context: Context): Int {
        val display = context.resources.displayMetrics
        return (display.widthPixels / 3)
    }
}
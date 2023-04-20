package com.illu.demo.ui.find

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.illu.demo.R
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class ImageAdapter(mDatas: List<Banner>) : BannerAdapter<Banner, ImageAdapter.BannerViewHolder>(mDatas) {

    class BannerViewHolder(@NonNull view: ImageView) :
        RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = BannerUtils.getView(parent, R.layout.banner_image) as ImageView
        BannerUtils.setBannerRound(imageView, 20f)
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: Banner?, position: Int, size: Int) {
//        Glide.with(holder?.itemView?.context)
//            .load(data?.imagePath)
//            .into(holder?.imageView)
    }
}
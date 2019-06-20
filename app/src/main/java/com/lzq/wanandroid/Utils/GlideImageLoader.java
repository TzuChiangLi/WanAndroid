package com.lzq.wanandroid.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.R;
import com.ms.banner.holder.BannerViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideImageLoader implements BannerViewHolder<Data> {
    private static final String TAG = "GlideImageLoader";
    @BindView(R.id.rv_home_img)
    ImageView mImgView;


    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_home_img_item, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Data data) {
        //Glide 加载图片简单用法
        Glide.with(context).load(data.getImagePath()).into(mImgView);
    }

}

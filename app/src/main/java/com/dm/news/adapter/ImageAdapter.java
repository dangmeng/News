package com.dm.news.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dm.news.R;
import com.dm.news.bean.MeiNvGson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2016/11/2.
 */

public class ImageAdapter extends RecyclerArrayAdapter<MeiNvGson.NewslistBean> {

    private  ImageView mImgPic;

    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(parent);
    }

    class ImageViewHolder extends BaseViewHolder<MeiNvGson.NewslistBean> {

        public ImageViewHolder(ViewGroup parent) {
            super(parent, R.layout.image_recycler_item);
            mImgPic = $(R.id.image_pic);
        }

        @Override
        public void setData(MeiNvGson.NewslistBean data) {
            Glide.with(getContext())
                    .load(data.getPicUrl())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .into(mImgPic);
        }
    }
}

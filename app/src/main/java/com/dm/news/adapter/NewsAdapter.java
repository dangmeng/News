package com.dm.news.adapter;


import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dm.news.R;
import com.dm.news.bean.NewsGson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NewsAdapter extends RecyclerArrayAdapter<NewsGson.NewslistBean>{

    private TextView tvName;
    private TextView tvSign;
    private ImageView ivFace;

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }

    private class NewsViewHolder extends BaseViewHolder<NewsGson.NewslistBean> {

        public NewsViewHolder(ViewGroup parent) {
            super(parent, R.layout.news_recyler_item);
            tvName =  $(R.id.person_name);
            tvSign =  $(R.id.person_sign);
            ivFace =  $(R.id.person_face);
        }

        @Override
        public void setData(NewsGson.NewslistBean data) {
            tvName.setText(data.getTitle());
            tvSign.setText(data.getCtime());
            Glide.with(getContext())
                    .load(data.getPicUrl())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(ivFace);
        }
    }
}

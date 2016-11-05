package com.dm.news.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dm.news.R;
import com.dm.news.activity.ImagePagerActivity;
import com.dm.news.adapter.ImageAdapter;
import com.dm.news.bean.MeiNvGson;
import com.dm.news.manager.RetrofitManager;
import com.dm.news.utils.PixUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/2.
 */

public class MeiZiFragment extends BaseFragment {

    private int page = 0;
    private Subscription mSubscribe;
    private EasyRecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ImageAdapter mImageAdapter;
    private List<MeiNvGson.NewslistBean> mNewslist;

    @Override
    protected void initData() {
        mSubscribe = RetrofitManager.getInstence().getApiService()
                .getPictureData("0271191a3d0bcd8483debff0c759f20a", "10", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiNvGson>() {
                    @Override
                    public void onStart() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mProgressBar.setVisibility(View.GONE);
                        showToast("网络连接失败！");
                    }

                    @Override
                    public void onNext(MeiNvGson meiNvGson) {
                        mNewslist =  meiNvGson.getNewslist();
                        mImageAdapter.addAll(mNewslist);
                    }
                });

        page = page + 1;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.common_fragment_layout,null,false);
        return view;
    }

    @Override
    protected void initView(@Nullable View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_news);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mImageAdapter = new ImageAdapter(getActivity());
        mRecyclerView.setAdapter(mImageAdapter);
        //设置分割线
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertDpToPixel(3,getActivity()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initListener() {

        mImageAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                initData();
            }

            @Override
            public void onMoreClick() {

            }
        });

        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mImageAdapter.clear();
                initData();
            }
        });

        mImageAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                Log.i("position-->", position + "");
                if (position > 9) {//避免下标越界
                    position = 0;
                }
                String[] urls = {mNewslist.get(position).getPicUrl()};
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        if (mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
        super.onDestroy();
    }

}

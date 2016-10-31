package com.dm.news.fragment;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dm.news.R;
import com.dm.news.adapter.NewsAdapter;
import com.dm.news.bean.NewsGson;
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
 * Created by dm on 2016/10/28.
 */

public class NewsFragment extends BaseFragment {


    private int page = 0;
    private Subscription mSubscribe;
    private NewsAdapter mNewsAdapter;
    private EasyRecyclerView mRecyclerView;
    private List<NewsGson.NewslistBean> mNewslist;
    private ProgressBar mProgressBar;

    @Override
    protected void initData() {
        //数据处理
        mSubscribe = RetrofitManager.getInstence()
                .getApiService()
                .getNewsData("0271191a3d0bcd8483debff0c759f20a", "10", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsGson>() {

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
                    public void onNext(NewsGson newsGson) {
                        mNewslist = newsGson.getNewslist();

                        mNewsAdapter.addAll(mNewslist);
                        mNewsAdapter.notifyDataSetChanged();
                    }

                });
        page = page + 1;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_news);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerView);
        mNewsAdapter = new NewsAdapter(getActivity());
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置分割线
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertDpToPixel(3,getActivity()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initListener() {
        /**RecycleView的加载更多*/
        mNewsAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                initData();
            }

            @Override
            public void onMoreClick() {

            }
        });
        /**RecycleView的下拉刷新*/
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsAdapter.clear();
                page = 0;
                initData();
            }
        });
        /**item的点击事件*/
        mNewsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewsGson.NewslistBean newslistBean = mNewslist.get(position);
                String picUrl = newslistBean.getPicUrl();
                String url = newslistBean.getUrl();
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

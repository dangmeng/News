package com.dm.news.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dm.news.R;

public class NewsDetailActivity extends BaseActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ImageView mNewsImage;
    private WebView mWebView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_news_detail);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_newsdetail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_newsdetail);
        mNewsImage = (ImageView) findViewById(R.id.iv_newsdetail);
        mWebView = (WebView) findViewById(R.id.web_text_newsdetail);

        mToolbar.setTitle("新闻详情");
        setSupportActionBar(mToolbar);
        //设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String picUrl = intent.getStringExtra("picUrl");
        String url = intent.getStringExtra("url");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl(url);

        Glide.with(this)
                .load(picUrl)
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .into(mNewsImage);
    }

    @Override
    protected void initListener() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

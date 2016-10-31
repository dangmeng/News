package com.dm.news;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dm.news.activity.BaseActivity;
import com.dm.news.bean.User;
import com.dm.news.fragment.NewsFragment;
import com.dm.news.manager.RetrofitManager;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String NEWSTAG = "news";
    public static final String MEIZI = "meizi";
    private Subscription mSubscribe;
    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvUrl;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //默认fragment
        NewsFragment newsFragment = new NewsFragment();
        addFragment(newsFragment,NEWSTAG);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerRootView = navigationView.getHeaderView(0);

        mIvAvatar = (ImageView) headerRootView.findViewById(R.id.iv_arv);
        mTvName = (TextView) headerRootView.findViewById(R.id.tv_user_name);
        mTvUrl = (TextView) headerRootView.findViewById(R.id.tv_github_url);
    }

    @Override
    protected void initData() {
        mSubscribe = RetrofitManager.getInstence().getApiService()
                .getUserInfo("https://api.github.com/users/dangmeng")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mTvName.setText("未知");
                        mTvUrl.setText("未知");
                        mIvAvatar.setImageResource(R.mipmap.ic_launcher);
                    }

                    @Override
                    public void onNext(User user) {
                        mTvName.setText(user.getLogin());
                        mTvUrl.setText(user.getHtml_url());
                        Glide.with(MainActivity.this)
                                .load(user.getAvatar_url())
                                .placeholder(R.drawable.ic_menu_gallery)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .override(80,80)
                                .into(mIvAvatar);
                    }
                });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            // Handle the camera action
            NewsFragment newsFragment = new NewsFragment();
            addFragment(newsFragment,NEWSTAG);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
        super.onDestroy();
    }
}

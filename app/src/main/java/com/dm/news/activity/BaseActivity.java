package com.dm.news.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dm.news.R;
import com.dm.news.manager.ActivityCollector;

/**
 * Created by Administrator on 2016/10/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add activities
        ActivityCollector.addActivity(this);
        Log.i(TAG,TAG + "create");
        initView();
        initData();
        initListener();
    }



    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //finish activity
        ActivityCollector.removeActivity(this);
        Log.i(TAG,TAG + "destroy");
    }

    protected void addFragment(Fragment fragment,String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_fragment,fragment,tag);
        transaction.commit();
    }

    protected void initData() {}
    protected void initListener() {}

}
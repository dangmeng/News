package com.dm.news.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dm on 2016/10/28.
 * activity管理类
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    /**
     * add activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * remove activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * finish all activities
     */
    public static void finishAllActivities() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

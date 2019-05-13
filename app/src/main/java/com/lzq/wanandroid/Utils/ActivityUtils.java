package com.lzq.wanandroid.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {
    private static FragmentManager fm;
    private static ActivityUtils INSTANCE;

    private ActivityUtils() {
    }

    public static ActivityUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityUtils();
        }
        return INSTANCE;
    }

    public static void addFragmentToActivity(FragmentManager fm, Fragment fragment, int id) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment).commitAllowingStateLoss();
        } else {
            transaction.add(id, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public static FragmentManager getFragmentManager() {
        return fm;
    }

}

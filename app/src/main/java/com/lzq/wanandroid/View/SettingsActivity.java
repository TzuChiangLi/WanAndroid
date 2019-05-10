package com.lzq.wanandroid.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.BaseActivity;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener {
    private static final String TAG = "SettingsActivity";
    @BindView(R.id.set_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.set_night_mode)
    SwitchButton mNightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS, MODE_PRIVATE).getBoolean(StringUtils.KEY_NIGHT_MODE, false);
        mNightBtn.setChecked(nightMode);
        mNightBtn.setOnCheckedChangeListener(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
                overridePendingTransition(R.anim.exit_fade_out, R.anim.exit_fade_in);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }


    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (isChecked) {
            ToastUtils("开");
            SPUtils.getInstance(StringUtils.CONFIG_SETTINGS, MODE_PRIVATE).put(StringUtils.KEY_NIGHT_MODE, true);
        } else {
            ToastUtils("关");
            SPUtils.getInstance(StringUtils.CONFIG_SETTINGS, MODE_PRIVATE).put(StringUtils.KEY_NIGHT_MODE, false);
        }
        Log.d(TAG, "----setNightMode: " + mNightBtn.isChecked());
        getDelegate().setLocalNightMode((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).getBoolean(StringUtils.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Event event = new Event();
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_CHANGE_DAY_NIGHT_MODE;
        EventBus.getDefault().post(event);
    }
}

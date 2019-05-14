package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class UserFragment extends BaseFragment {
    private static final String TAG = "UserFragment";
    @BindView(R.id.user_tv_name)
    TextView mUserNameTv;
    @BindView(R.id.user_tv_id)
    TextView mIDTv;


    public static UserFragment newInstance() {
        return new UserFragment();
    }

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(StringUtils.URL);
        List<Cookie> cookies = cookieStore.getCookie(httpUrl);
        if (cookies.size()!=0||cookies!=null){
            mIDTv.setText(String.valueOf(SPUtils.getInstance("userinfo").getInt("id")));
            mUserNameTv.setText(SPUtils.getInstance("userinfo").getString("username").toString());
        }
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_USER) {
            if (event.type == Event.TYPE_LOGIN_SUCCESS) {
                mIDTv.setText(String.valueOf(SPUtils.getInstance("userinfo").getInt("id")));
                mUserNameTv.setText(SPUtils.getInstance("userinfo").getString("username"));
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "----onDestroy: ");
    }
}

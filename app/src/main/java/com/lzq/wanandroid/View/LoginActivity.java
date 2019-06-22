package com.lzq.wanandroid.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.Notification;
import com.lzq.wanandroid.Base.BaseActivity;
import com.lzq.wanandroid.Api.LoginContract;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Presenter.LoginPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Custom.ClearEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_KONGZUE;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView, OnTitleBarListener {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.login_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.cardView)
    CardView mCardView;
    @BindView(R.id.user_edt_id)
    ClearEditText mIDEdt;
    @BindView(R.id.user_edt_pwd)
    ClearEditText mPwdEdt;
    @BindView(R.id.user_edt_confirm)
    ClearEditText mConfirmEdt;
    @BindView(R.id.user_btn_login)
    Button mLoginBtn;
//    @BindView(R.id.user_btn_register_now)
//    TextView mRegisterNowBtn;
    @BindView(R.id.user_ll_confirm)
    LinearLayout mConfirmView;
    @BindView(R.id.user_line_confirm)
    View mLineView;
    @BindView(R.id.login_tv_title_1st)
    TextView mFirstTv;
    @BindString(R.string.user_btn_login)
    String strLogin;
    @BindString(R.string.user_btn_register)
    String strRegister;
    @BindView(R.id.login_ll_title)
    View mTitleView;
    private ConstraintLayout.LayoutParams mLoginLayout = null;
    private LoginContract.LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        EventBus.getDefault().register(this);
        doAnimation(0);
        mTitleBar.setRightTitle("注册");
        mTitleBar.setOnTitleBarListener(this);
        mLoginLayout = (ConstraintLayout.LayoutParams) mLoginBtn.getLayoutParams();
        if (mPresenter == null) {
            WebTask mTask = WebTask.getInstance();
            mPresenter = LoginPresenter.createPresenter(this, mTask);
        }
        ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    mPresenter.resetLoginLocation(LoginActivity.this);
                } catch (Exception e) {
                }
            }
        };
        mIDEdt.getViewTreeObserver().addOnGlobalLayoutListener(mListener);
        TextView.OnEditorActionListener mTvListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()) {
                    Login();
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    showSoftInputUtil(mPwdEdt);
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Login();
                    return true;
                }
                return false;
            }
        };
        mIDEdt.setOnEditorActionListener(mTvListener);
        mPwdEdt.setOnEditorActionListener(mTvListener);
    }

    @Override
    public void LoginSuccess(Data data) {
        ToastUtils.show("登录成功！");
        SPUtils.getInstance("userinfo", MODE_PRIVATE).put("isLogin", true);
        SPUtils.getInstance("userinfo", MODE_PRIVATE).put("username", data.getUsername());
        SPUtils.getInstance("userinfo", MODE_PRIVATE).put("id", String.valueOf(data.getId()));
        Event event = new Event();
        event.target = Event.TARGET_USER;
        event.type = Event.TYPE_LOGIN_SUCCESS;
        EventBus.getDefault().post(event);
        event.target = Event.TARGET_COLLECT;
        event.type = Event.TYPE_LOGIN_SUCCESS;
        EventBus.getDefault().post(event);
        finish();
        //不必传cookie，只需要传返回的结果，用Presenter层来实现
        //cookie在App启动的时候检测一下size和null，如果为0或者为null，就说明需要登录
    }

    @Override
    public void RegisterResult(String... infos) {
        if (!TextUtils.isEmpty(infos[0])) {
            ToastUtils(infos[0]);
        }
    }

    @Override
    public void setLoginLocation(int height) {
        mLoginLayout.bottomMargin = 16;//此处单位是dp
        mLoginBtn.setLayoutParams(mLoginLayout);
//        mRegisterNowBtn.setVisibility(View.GONE);
    }

    @Override
    public void refreshLocation(int height) {
        mLoginLayout.bottomMargin = ConvertUtils.dp2px(100);
        mLoginBtn.setLayoutParams(mLoginLayout);
//        mRegisterNowBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mPresenter = presenter;
    }

//    @OnClick(R.id.user_btn_register_now)
//    public void Register() {
//        if (mLoginBtn.getText().toString().equals("登录")) {
//            mTitleBar.setRightTitle("登录");
////            mRegisterNowBtn.setText("登录");
//            mLoginBtn.setText(strRegister);
//            doAnimation(2);
//
//        } else {
//            mTitleBar.setRightTitle("注册");
////            mRegisterNowBtn.setText("注册");
//            mLoginBtn.setText(strLogin);
//            doAnimation(1);
//
//        }
//    }

    @OnClick(R.id.user_btn_login)
    public void Login() {
        if (!TextUtils.isEmpty(mIDEdt.getText().toString()) && !TextUtils.isEmpty(mPwdEdt.getText().toString())) {
            if (mLoginBtn.getText().toString().equals("登录")) {
                mPresenter.doLogin(mIDEdt.getText().toString(), mPwdEdt.getText().toString());
            }
            if (mLoginBtn.getText().toString().equals("注册")) {
                if (!TextUtils.isEmpty(mConfirmEdt.getText().toString())) {
                    if (mPwdEdt.getText().toString().equals(mConfirmEdt.getText().toString())) {
                        mPresenter.doRegister(mIDEdt.getText().toString(), mPwdEdt.getText().toString());
                    } else {
                        ToastUtils("两次输入的密码不一致！");
                        return;
                    }
                } else {
                    ToastUtils("请二次确认密码");
                }
            }
        } else if (TextUtils.isEmpty(mPwdEdt.getText().toString()) && TextUtils.isEmpty(mIDEdt.getText().toString())) {
            ToastUtils("请输入用户名和密码");
            return;
        } else if (TextUtils.isEmpty(mIDEdt.getText().toString())) {
            ToastUtils("尚未输入用户名");
            return;
        } else {
            ToastUtils("尚未输入密码");
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MAIN) {
            if (event.type == Event.TYPE_LOGIN_ANIMATION) {
                doAnimation(0);
            }
        }
    }

    public void doAnimation(int flag) {
        ObjectAnimator alpha_TitleView_hide = ObjectAnimator.ofFloat(mTitleView, "alpha", 1, 0);
        final ObjectAnimator alpha_TitleView_show = ObjectAnimator.ofFloat(mTitleView, "alpha", 0, 1);

        Float OldCardHeight = mCardView.getTranslationY();
        Float OldLoginBtnHeight = mLoginBtn.getTranslationY();
        Float OldRegisterBtnHeight = mTitleBar.getTranslationY();


        ObjectAnimator alpha_CardView = ObjectAnimator.ofFloat(mCardView, "alpha", 0, 1);
        ObjectAnimator alpha_RegisterNow = ObjectAnimator.ofFloat(mTitleBar, "alpha", 0, 1);
        ObjectAnimator alpha_LoginBtn = ObjectAnimator.ofFloat(mLoginBtn, "alpha", 0, 1);


        ObjectAnimator translationY_CardView = ObjectAnimator.ofFloat(mCardView, "translationY", mCardView.getTranslationY(), -45, OldCardHeight + 20, OldCardHeight);
        ObjectAnimator translationY_LoginBtn = ObjectAnimator.ofFloat(mLoginBtn, "translationY", mLoginBtn.getTranslationY(), -45, OldCardHeight + 20, OldLoginBtnHeight);
        ObjectAnimator translationY_RegisterNow = ObjectAnimator.ofFloat(mTitleBar, "translationY", mTitleBar.getTranslationY(), -45, OldCardHeight + 20, OldRegisterBtnHeight);

        ObjectAnimator alpha_line_hide = ObjectAnimator.ofFloat(mLineView, "alpha", 1, 0);
        ObjectAnimator alpha_confirm_hide = ObjectAnimator.ofFloat(mConfirmView, "alpha", 1, 0);
        switch (flag) {
            case 0://显示
                try {

                    AnimatorSet alphaSet = new AnimatorSet();
                    AnimatorSet translationSet = new AnimatorSet();

                    alphaSet.playTogether(alpha_CardView, alpha_LoginBtn, alpha_RegisterNow);
                    alphaSet.setDuration(1000);

                    translationSet.playTogether(translationY_CardView, translationY_RegisterNow);
                    translationSet.setDuration(1000);

                    translationY_LoginBtn.setDuration(1000).setStartDelay(100);

                    //好戏上演
                    alphaSet.start();
                    translationSet.start();
                    translationY_LoginBtn.start();
                } catch (Exception e) {
                    Log.d(TAG, "----doAnimation: " + e.getMessage());
                }
                break;
            case 1://注册消失,alpha--1--0
                alpha_TitleView_hide.setDuration(250).start();
                alpha_TitleView_hide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFirstTv.setText(strLogin);
                        alpha_TitleView_show.setDuration(250).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                AnimatorSet alphaHide = new AnimatorSet();
                alphaHide.playTogether(alpha_line_hide, alpha_confirm_hide);
                alphaHide.setDuration(500).start();
                alphaHide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLineView.setVisibility(View.GONE);
                        mConfirmView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                break;
            case 2://注册显示,alpha--0--1
                alpha_TitleView_hide.setDuration(250).start();
                alpha_TitleView_hide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mFirstTv.setText(strRegister);
                        alpha_TitleView_show.setDuration(250).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mLineView.setVisibility(View.VISIBLE);
                mConfirmView.setVisibility(View.VISIBLE);
                ObjectAnimator alpha_line_show = ObjectAnimator.ofFloat(mLineView, "alpha", 0, 1);
                ObjectAnimator alpha_confirm_show = ObjectAnimator.ofFloat(mConfirmView, "alpha", 0, 1);
                AnimatorSet alphaShow = new AnimatorSet();
                alphaShow.playTogether(alpha_line_show, alpha_confirm_show);
                alphaShow.setDuration(1000).start();
                break;

            default:
                break;
        }
    }


    @Override
    public void onLeftClick(View v) {

    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {

        if (mLoginBtn.getText().toString().equals("登录")) {
            mTitleBar.setRightTitle("登录");
            mLoginBtn.setText(strRegister);
            doAnimation(2);

            Notification.show(this, 0, "注册账号无需使用手机、邮箱等个人信息。",Notification.TYPE_ERROR ).setDialogStyle(STYLE_KONGZUE);
        } else {
            mTitleBar.setRightTitle("注册");
            mLoginBtn.setText(strLogin);
            doAnimation(1);

        }
    }
}

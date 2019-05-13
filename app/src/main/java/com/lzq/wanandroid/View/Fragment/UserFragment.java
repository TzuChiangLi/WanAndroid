package com.lzq.wanandroid.View.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.LoginContract;
import com.lzq.wanandroid.Net.LoginTask;
import com.lzq.wanandroid.Presenter.LoginPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.AnimationUtil;
import com.lzq.wanandroid.View.Custom.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends BaseFragment implements LoginContract.LoginView {
    private static final String TAG = "UserFragment";
    @BindView(R.id.cardView)
    CardView mCardView;
    @BindView(R.id.user_edt_id)
    ClearEditText mIDEdt;
    @BindView(R.id.user_edt_pwd)
    ClearEditText mPwdEdt;
    @BindView(R.id.user_btn_login)
    Button mLoginBtn;
    @BindView(R.id.user_btn_register_now)
    Button mRegisterNowBtn;
    private ConstraintLayout.LayoutParams mLoginLayout = null;
    private LoginContract.LoginPresenter mPresenter;

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
        doAnimation();
        mLoginLayout = (ConstraintLayout.LayoutParams) mLoginBtn.getLayoutParams();
        if (mPresenter == null) {
            LoginTask mTask = LoginTask.getInstance();
            mPresenter = LoginPresenter.createPresenter(this, mTask);
        }
        ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPresenter.resetLoginLocation(getActivity());
            }
        };
        mIDEdt.getViewTreeObserver().addOnGlobalLayoutListener(mListener);
        TextView.OnEditorActionListener mTvListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()) {
                    Login();
                    Log.d(TAG, "----onEditorAction: 回车");
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    showSoftInputUtil(mPwdEdt);
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "----onEditorAction: 完成");
                    Login();
                    return true;
                }
                return false;
            }
        };
        mIDEdt.setOnEditorActionListener(mTvListener);
        mPwdEdt.setOnEditorActionListener(mTvListener);
        return view;
    }


    @Override
    public void LoginSuccess() {
        ToastUtils.show("登录成功！");
    }

    @Override
    public void setLoginLocation(int height) {
        mLoginLayout.bottomMargin = 16;//此处单位是dp
        mLoginBtn.setLayoutParams(mLoginLayout);
        mRegisterNowBtn.setVisibility(View.GONE);
    }

    @Override
    public void refreshLocation(int height) {
        mLoginLayout.bottomMargin = ConvertUtils.dp2px(100);
        mLoginBtn.setLayoutParams(mLoginLayout);
        mRegisterNowBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.user_btn_login)
    public void Login() {
        if (!TextUtils.isEmpty(mIDEdt.getText().toString()) && !TextUtils.isEmpty(mPwdEdt.getText().toString())) {
            mPresenter.doLogin(mIDEdt.getText().toString(), mPwdEdt.getText().toString());
        } else if (TextUtils.isEmpty(mPwdEdt.getText().toString()) && TextUtils.isEmpty(mIDEdt.getText().toString())) {
            ToastUtils.show("请输入用户名和密码");
            return;
        } else if (TextUtils.isEmpty(mIDEdt.getText().toString())) {
            ToastUtils.show("尚未输入用户名");
            return;
        } else {
            ToastUtils.show("尚未输入密码");
            return;
        }
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mPresenter = presenter;
    }

    public void doAnimation() {
        Float Height = ((Float.valueOf(AnimationUtil.getScreenHeight(getActivity()))) / 40) * 1;
        Float OldCardHeight = mCardView.getTranslationY();
        Float OldLoginBtnHeight = mLoginBtn.getTranslationY();
        Float OldRegisterBtnHeight = mRegisterNowBtn.getTranslationY();

        ObjectAnimator alpha_CardView = ObjectAnimator.ofFloat(mCardView, "alpha", 0, 1);
        ObjectAnimator alpha_RegisterNow = ObjectAnimator.ofFloat(mRegisterNowBtn, "alpha", 0, 1);
        ObjectAnimator alpha_LoginBtn = ObjectAnimator.ofFloat(mLoginBtn, "alpha", 0, 1);


        ObjectAnimator translationY_CardView = ObjectAnimator.ofFloat(mCardView, "translationY", mCardView.getTranslationY(), -Height, OldCardHeight + 20, OldCardHeight);
        Log.d(TAG, "----doAnimation: "+mCardView.getTranslationY()+"/"+(-Height)+"/"+(OldCardHeight +50)+"/"+OldCardHeight);
        ObjectAnimator translationY_LoginBtn = ObjectAnimator.ofFloat(mLoginBtn, "translationY", mLoginBtn.getTranslationY(), -Height, OldCardHeight + 20, OldLoginBtnHeight);
        ObjectAnimator translationY_RegisterNow = ObjectAnimator.ofFloat(mRegisterNowBtn, "translationY", mRegisterNowBtn.getTranslationY(), -Height, OldCardHeight + 20, OldRegisterBtnHeight);

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

    }

}

package com.zhowin.motorbike.common.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


import com.gyf.barlibrary.ImmersionBar;
import com.zhowin.motorbike.R;
import com.zhowin.motorbike.common.view.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment extends SupportFragment {

    public BaseActivity mActivity;
    public BaseActivity mContext;
    public View rootView;
    Unbinder unbinder;
    protected LoadProgressDialog progressDialog;
    protected int totalPage;//总页数
    protected int currentPage;//当前页数
    protected int pageNumber = 10; //每一页的个数

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        mContext=mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = View.inflate(mActivity, getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initTitleBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.red_color)
                .statusBarDarkFont(true, 0f)
                .init();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public void initListener() {
    }

    public <E extends View> E get(int id) {
        return (E) rootView.findViewById(id);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initTitleBar();
    }

    /**
     * 注册事件通知
     */
    public void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ImmersionBar.with(this).destroy();
    }

    public void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public LoadProgressDialog showLoadDialog() {
        return showLoadDialog(mActivity.getString(R.string.Loading));
    }

    /**
     * 显示对话框
     */
    public LoadProgressDialog showLoadDialog(String hitMessage) {
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mActivity);
            if (TextUtils.isEmpty(hitMessage)) {
                progressDialog = progressDialog.createLoadingDialog(mActivity.getString(R.string.Loading));
            } else {
                progressDialog = progressDialog.createLoadingDialog(hitMessage);
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * 关闭提示框
     */
    public void dismissLoadDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.stopAnimator();
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    /**
     * 跳转到外部浏览器
     */
    protected void jumpToAndroidBrowser(String webUrl) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_uri_browsers = Uri.parse(webUrl);
        intent.setData(content_uri_browsers);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        mActivity.startActivity(intent);
    }
}

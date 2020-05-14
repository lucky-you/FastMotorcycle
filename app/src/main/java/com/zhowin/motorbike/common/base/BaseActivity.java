package com.zhowin.motorbike.common.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.zhowin.motorbike.R;
import com.zhowin.motorbike.common.view.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity extends SupportActivity {
    Unbinder unbinder;
    protected AppCompatActivity mContext;
    protected LoadProgressDialog progressDialog;

    protected int totalPage;//总页数
    protected int currentPage = 1;//当前页数
    protected int pageNumber = 10; //每一页的个数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //设定为竖屏
        setContentView(getLayoutId());
        ActivityManager.getAppInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        initImmersionBar();
        initView();
        initData();
        initListener();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public void initListener() {
    }

    public <E extends View> E get(int id) {
        return (E) findViewById(id);
    }

    public void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.red_color)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0f)
                .init();
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
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        ActivityManager.getAppInstance().removeActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
        mContext = null;
    }


    public LoadProgressDialog showLoadDialog() {
        return showLoadDialog(mContext.getString(R.string.Loading));
    }

    /**
     * 显示对话框
     */
    public LoadProgressDialog showLoadDialog(String hitMessage) {
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            if (TextUtils.isEmpty(hitMessage)) {
                progressDialog = progressDialog.createLoadingDialog(mContext.getString(R.string.Loading));
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
     * 跳转外部链接
     */
    protected void jumpToAndroidBrowser(String webUrl) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_uri_browsers = Uri.parse(webUrl);
        intent.setData(content_uri_browsers);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        mContext.startActivity(intent);
    }

}

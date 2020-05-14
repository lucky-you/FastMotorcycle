package com.zhowin.motorbike.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.motorbike.R;


public class RefreshLayout extends SwipeRefreshLayout {

    public RefreshLayout(@NonNull Context context) {
        super(context);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.red_color, R.color.red_color, R.color.red_color);
    }
}

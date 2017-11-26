package com.oab.skyi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oab.skyi.R;
import com.oab.skyi.common.BaseActivity;

import butterknife.OnClick;

/**
 * Created by bao on 2017/11/26.
 */

public class CreateLiveActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live);
    }

    @OnClick(R.id.bt_open_live)
    public void openLive() {
    }
}

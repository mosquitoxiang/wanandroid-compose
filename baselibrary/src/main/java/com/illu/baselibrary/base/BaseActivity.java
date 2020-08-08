package com.illu.baselibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.illu.baselibrary.R;
import com.zhangyu.util.EventBusUtils;


public abstract class BaseActivity extends AppCompatActivity implements OnTitleBarListener {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    public final Object mHandlerToken = hashCode();

    public final boolean postDelayed(Runnable r, long delayMillis){
        if (delayMillis < 0){
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
        getLifecycle().addObserver();
    }

    public final boolean postAtTime(Runnable r, long uptimeMillis){
        return HANDLER.postAtTime(r, mHandlerToken, uptimeMillis);
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getProviderId());
        if (isRegisterEventbus()){
            EventBusUtils.INSTANCE.register(this);
        }
        initView();
        initImmer();
    }

    protected abstract int getProviderId();

    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        HANDLER.removeCallbacksAndMessages(mHandlerToken);
        super.onDestroy();
        if (isRegisterEventbus()){
            EventBusUtils.INSTANCE.unRegister(this);
        }
    }

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivityFinish(new Intent(this, cls));
    }

    public void startActivityFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public void onRightClick(View v) {}

    @Override
    public void onTitleClick(View v) {}

    private void initImmer(){
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(isDark())
                .statusBarColor(setStatusBarColor())
                .fitsSystemWindows(true)
                .init();
    }

    protected boolean isDark(){
        return true;
    }

    protected int setStatusBarColor(){
        return R.color.white;
    }

    protected boolean isRegisterEventbus(){
        return false;
    }

    protected void hideBottomMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}

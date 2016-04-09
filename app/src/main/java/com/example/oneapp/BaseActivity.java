package com.example.oneapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    Toast mToast;


    /**
     * 初始化TOOLBAR,不带返回键
     * @param title 标题
     */
    public void initToolbar(String title) {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化TOOLBA，带返回键
     * isShow=true时默认返回点击时间为finish()
     * @param title 标题
     * @param isShow
     */
    public void initToolbar(int title,Boolean isShow) {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);//显示返回按键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 显示Toast
     * @param text 提示文字
     */
    public void ShowToast(final String text){
        if (!TextUtils.isEmpty(text)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null){
                        mToast = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    /**
     * 显示Toast
     * @param resId R.String
     */
    public void ShowToast(final int resId){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null){
                    mToast = Toast.makeText(getApplicationContext(),resId,Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }
        });
    }


}

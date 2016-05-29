package com.example.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapp.R;

/**
 * Created by RImpression on 2016/5/29 0029.
 */
public class PariseUtil {

    /**
     * 点赞效果
     * @param context 上下文
     * @param tv        TextView
     * @param isClick 是否已经点赞
     * @return 已经点赞返回true,否则返回false
     */
    public Boolean PariseClick(Context context, TextView tv, Boolean isClick) {
        Drawable imgPress = context.getResources().getDrawable(R.mipmap.icon_parise_press);
        Drawable imgNormal = context.getResources().getDrawable(R.mipmap.icon_parise_nomal);
        imgPress.setBounds(0, 0, imgPress.getMinimumWidth(), imgPress.getMinimumHeight());
        imgNormal.setBounds(0,0,imgNormal.getMinimumWidth(),imgNormal.getMinimumHeight());

        Drawable[] IMG = tv.getCompoundDrawables();
        String s = tv.getText().toString();
        if (s == "") {
            tv.setText("1");
            tv.setCompoundDrawables(imgPress,null,null,null);
            isClick = true;
        } else {
            int pasNum = Integer.valueOf(s).intValue();
            if (!isClick) {
                tv.setCompoundDrawables(imgPress,null,null,null);
                pasNum++;
                tv.setText(String.valueOf(pasNum));
                isClick = true;
            } else {
                tv.setCompoundDrawables(imgNormal,null,null,null);
                pasNum--;
                tv.setText(String.valueOf(pasNum));
                isClick = false;
            }
        }

        return isClick;
    }
}

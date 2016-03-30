package com.example.oneapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.entity.ExtractEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentExtract extends Fragment {
    View view;
    private String mDate = null;
    private String mNum = "1";
    private Button btnExtract;
    private String url = "http://rest.wufazhuce.com/OneForWeb/one/getHp_N";
    private String[] mPara1,mPara2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取系统当前时间
        mDate = getDate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("lifeResult", "onCreateView");
        view = inflater.inflate(R.layout.fragment_extract, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("lifeResult", "onActivityCreated");
        initViews();

        mPara1 = new String[]{"strDate", "strRow"};
        mPara2 = new String[]{mDate,mNum};

        btnExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExtractRequest(url,mPara1,mPara2);
            }
        });

    }


    private void initViews() {

    }

    /**
     * 请求摘录数据
     * @param Url API
     * @param param1 POST参数
     * @param param2 POST参数的值
     */
    private void getExtractRequest(String Url, String[] param1, String[] param2) {
        new MyRequest(getContext().getApplicationContext()).postRequest(Url, param1, param2, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //parse2Jason(result);
                Log.i("ExtractResult", result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getContext().getApplicationContext(), "数据请求失败", Toast.LENGTH_SHORT).show();
                Log.i("ExtractResult", volleyError.toString());
            }
        });
    }


    /**
     * json解析
     * @param result
     */
    public ExtractEntity parse2Jason(String result){
        try {
            ExtractEntity extractEntity = new ExtractEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("hpEntity");
            extractEntity.setStrLastUpdateDate(object.getString("strLastUpdateDate"));
            extractEntity.setStrHpId(object.getString("strHpId"));
            extractEntity.setHpTitle(object.getString("strHpTitle"));
            extractEntity.setStrThumbnailUrl(object.getString("strThumbnailUrl"));
            extractEntity.setStrAuthor(object.getString("strAuthor"));
            extractEntity.setStrContent(object.getString("strContent"));
            extractEntity.setStrMarketTime(object.getString("strMarketTime"));
            extractEntity.setStrPn(object.getString("strPn"));
            return extractEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("lifeResult", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("lifeResult","onPause");
    }

    //获取系统当前日期时间
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date());
    }
}

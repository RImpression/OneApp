package com.example.oneapp.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.entity.ExtractEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.example.oneapp.R;
import com.example.utils.PariseUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by RImpression on 2016/3/20 0020.
 */
public class FragmentExtract extends Fragment implements View.OnClickListener {
    private String mDate = null;
    private String mNum = "1";
    private String url = "http://rest.wufazhuce.com/OneForWeb/one/getHp_N";
    private String[] mPara1,mPara2;
    private Boolean isFirst = true;
    private ExtractEntity mExtract = null;
    private TextView tvHpTitle,tvAuthor,tvContent;
    private TextView tvBook,tvZan,tvShare;
    private ImageView imgShow;
    private View view;
    private Boolean isClick = false;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取系统当前时间
        mDate = getDate();
        mPara1 = new String[]{"strDate", "strRow"};
        mPara2 = new String[]{mDate,mNum};
        if (isFirst == true) {
            Log.i("lifeResult","onCreat");
            getExtractRequest(url,mPara1,mPara2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("lifeResult", "onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_extract, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("lifeResult", "onActivityCreated");
        initViews();
        if (isFirst == false){
            setDataWithView();
        }
    }


    private void initViews() {
        tvHpTitle = (TextView) getView().findViewById(R.id.tvHpTitle);
        tvAuthor = (TextView) getView().findViewById(R.id.tvAuthor);
        tvContent = (TextView) getView().findViewById(R.id.tvContent);
        imgShow = (ImageView) getView().findViewById(R.id.imgShow);
        tvBook = (TextView) getView().findViewById(R.id.tvBook);
        tvZan = (TextView) getView().findViewById(R.id.tvZan);
        tvShare = (TextView) getView().findViewById(R.id.tvShare);
        tvBook.setOnClickListener(this);
        tvZan.setOnClickListener(this);
        tvShare.setOnClickListener(this);
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
                mExtract = parse2Jason(result);
                isFirst = false;
                setDataWithView();
                //Log.i("lifeRestlt", result);
            }

            @Override
            public void onError(VolleyError volleyError) {
                Toast.makeText(getContext().getApplicationContext(), "数据请求失败", Toast.LENGTH_SHORT).show();
                Log.i("ExtractResult", volleyError.toString());
                isFirst = true;
            }
        });
    }

    private void setDataWithView() {
        tvHpTitle.setText(mExtract.getHpTitle());
        tvAuthor.setText(mExtract.getStrAuthor());
        tvContent.setText(mExtract.getStrContent());
        Picasso.with(getContext()).load(mExtract.getStrThumbnailUrl()).into(imgShow);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvBook:
                Toast.makeText(getContext(),"功能未开放",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvZan:
                isClick = new PariseUtil().PariseClick(getContext(),tvZan,isClick);
                break;

            case R.id.tvShare:
                Toast.makeText(getContext(),"功能未开放",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

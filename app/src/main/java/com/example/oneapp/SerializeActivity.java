package com.example.oneapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.entity.SerializeEntity;
import com.example.https.MyRequest;
import com.example.interfaces.HttpListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lcr on 16/4/9.
 */
public class SerializeActivity extends BaseActivity implements View.OnClickListener {

    private static final String URL_SERIALIZE= "http://v3.wufazhuce.com:8000/api/serialcontent/";
    private String URL_SERIALIZE_CONTENT;
    private String ID;
    private SerializeEntity serializeEntity = null;
    private TextView tvAuthorName,tvAuthorTime,tvSerlTitle,tvSerlContent,tvPraise,tvComment,tvShare,tvEditor;
    private ImageView imgAuthor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize);
        initToolbar(R.string.serialize,true);
        ID = getIntent().getStringExtra("ID");
        URL_SERIALIZE_CONTENT = URL_SERIALIZE+ID+"?";
        requestSerializeData();
        initViews();
    }

    private void initViews() {
        tvAuthorName = (TextView) findViewById(R.id.tvAuthorname);
        tvAuthorTime = (TextView) findViewById(R.id.tvAuthorTime);
        tvSerlTitle = (TextView) findViewById(R.id.tvSerlTtile);
        tvSerlContent = (TextView) findViewById(R.id.tvSerlContent);
        tvEditor = (TextView) findViewById(R.id.tvEditor);
        tvPraise = (TextView) findViewById(R.id.tvPraise);
        tvComment = (TextView) findViewById(R.id.tvComment);
        tvShare = (TextView) findViewById(R.id.tvShare);
        imgAuthor = (ImageView) findViewById(R.id.imgAuthor);

        tvPraise.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        imgAuthor.setOnClickListener(this);
    }

    /**
     * 请求连载数据
     */
    private void requestSerializeData() {
        new MyRequest(this).getRequest(URL_SERIALIZE_CONTENT, new HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("result",result);
                parse2Json(result);
                loadView();
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.i("result",volleyError.toString());
                ShowToast("请求数据错误");
            }
        });

    }

    /**
     * 加载布局
     */
    private void loadView() {
        tvAuthorName.setText(serializeEntity.getUser_name());
        tvAuthorTime.setText(serializeEntity.getMaketime());
        tvSerlTitle.setText(serializeEntity.getTitle());
        tvSerlContent.setText(Html.fromHtml(serializeEntity.getContent()));
        tvEditor.setText(serializeEntity.getCharge_edit());
        tvPraise.setText(String.valueOf(serializeEntity.getPraisenum()));
        tvComment.setText(String.valueOf(serializeEntity.getCommentnum()));
        tvShare.setText(String.valueOf(serializeEntity.getSharenum()));
        Picasso.with(this).load(serializeEntity.getWeb_url()).into(imgAuthor);
    }

    /**
     * 解析连载数据
     * @param result
     * @return serializeEntity
     */
    private SerializeEntity parse2Json(String result) {
        try {
            serializeEntity = new SerializeEntity();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject object = jsonObject.getJSONObject("data");
            serializeEntity.setId(object.getString("id"));
            serializeEntity.setSerial_id(object.getString("serial_id"));
            serializeEntity.setNumber(object.getString("number"));
            serializeEntity.setTitle(object.getString("title"));
            serializeEntity.setExcerpt(object.getString("excerpt"));
            serializeEntity.setContent(object.getString("content"));
            serializeEntity.setCharge_edit(object.getString("charge_edt"));
            serializeEntity.setRead_num(object.getInt("read_num"));
            serializeEntity.setMaketime(object.getString("maketime"));
            serializeEntity.setLast_update_date(object.getString("last_update_date"));
            serializeEntity.setAudio(object.getString("audio"));
            serializeEntity.setInput_name(object.getString("input_name"));
            serializeEntity.setLast_update_name(object.getString("last_update_name"));
            serializeEntity.setPraisenum(object.getInt("praisenum"));
            serializeEntity.setSharenum(object.getInt("sharenum"));
            serializeEntity.setCommentnum(object.getInt("commentnum"));
            JSONObject authorObject = object.getJSONObject("author");
            serializeEntity.setUser_id(authorObject.getString("user_id"));
            serializeEntity.setUser_name(authorObject.getString("user_name"));
            serializeEntity.setWeb_url(authorObject.getString("web_url"));
            serializeEntity.setDesc(authorObject.getString("desc"));

            //Log.i("json",serializeEntity.toString());
            return serializeEntity;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAuthor:
                ShowToast("功能未开发");
                break;
            case R.id.tvPraise:
                ShowToast("功能未开发");
                break;
            case R.id.tvComment:
                ShowToast("功能未开发");
                break;
            case R.id.tvShare:
                ShowToast("功能未开发");
                break;
        }

    }
}

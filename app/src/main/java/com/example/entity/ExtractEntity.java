package com.example.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RImpression on 2016/3/25 0025.
 * 摘录实体
 */
public class ExtractEntity {
    //最后更新时间
    private String strLastUpdateDate;
    //摘录ID
    private String strHpId;
    //标题号
    private String HpTitle;
    //缩略图URL
    private String StrThumbnailUrl;
    //作者
    private String strAuthor;
    //正文
    private String strContent;
    //匹配时间
    private String strMarketTime;

    private String strPn;


    public String getStrLastUpdateDate() {
        return strLastUpdateDate;
    }

    public void setStrLastUpdateDate(String strLastUpdateDate) {
        this.strLastUpdateDate = strLastUpdateDate;
    }

    public String getStrHpId() {
        return strHpId;
    }

    public void setStrHpId(String strHpId) {
        this.strHpId = strHpId;
    }

    public String getHpTitle() {
        return HpTitle;
    }

    public void setHpTitle(String hpTitle) {
        HpTitle = hpTitle;
    }

    public String getStrThumbnailUrl() {
        return StrThumbnailUrl;
    }

    public void setStrThumbnailUrl(String strThumbnailUrl) {
        StrThumbnailUrl = strThumbnailUrl;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrAuthor() {
        return strAuthor;
    }

    public void setStrAuthor(String strAuthor) {
        this.strAuthor = strAuthor;
    }

    public String getStrPn() {
        return strPn;
    }

    public void setStrPn(String strPn) {
        this.strPn = strPn;
    }

    public String getStrMarketTime() {
        return strMarketTime;
    }

    public void setStrMarketTime(String strMarketTime) {
        this.strMarketTime = strMarketTime;
    }



    /**
     * json解析
     * @param result
     */
    public static ExtractEntity parse2Jason(String result){
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

}

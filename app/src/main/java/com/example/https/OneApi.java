package com.example.https;


/**
 * Created by RImpression on 2017/1/4 0004.
 */
public class OneApi {
    public static final String URL = "http://v3.wufazhuce.com:8000";
    //每日摘要
    public static final String URL_EXTRACT = "http://rest.wufazhuce.com/OneForWeb/one/getHp_N";
    //文章数据
    public static final String URL_ARTICLE = URL + "/api/reading/index/0?";
    //轮播图片
    public static final String URL_BANNER_PHOTO = URL + "/api/reading/carousel/?";
    //音乐列表
    public static final String URL_MUSIC_LIST = URL + "/api/music/idlist/0?";
    //音乐详情：url +id+ "?"
    public static final String URL_MUSIC = URL + "/api/music/detail/";
    //音乐评论：url +id+ "/0?"
    public static final String URL_MUSIC_COMMENT = URL + "/api/comment/praiseandtime/music/";
    //近期电影信息列表
    public static final String URL_MOVIE_LIST = URL + "/api/movie/list/0?";
    //短篇详情： url +id+ "?"
    public static final String URL_ESSAY = URL + "/api/essay/";
    //短篇评论： url +id+ "/0?"
    public static final String URL_ESSAY_COMMENT = URL + "/api/comment/praiseandtime/essay/";
    //问答详情： url +id+ "?"
    public static final String URL_QUESTION =  URL + "/api/question/";
    //问答评论： url +id+ "/0?"
    public static final String URL_QUESTION_COMMMENT =  URL + "/api/comment/praiseandtime/question/";
    //连载问答详情： url +id+ "?"
    public static final String URL_SERIALIZE = URL + "/api/serialcontent/";
    //连载评论： url +id+ "/0?"
    public static final String URL_SERIALIZE_COMMENT = URL + "/api/comment/praiseandtime/serial/";
    //电影详情： url +id+ "?"
    public static final String URL_MOVIE = URL + "/api/movie/detail/";
    //电影故事： url +id+ "/story/1/0?"
    public static final String URL_MOVIE_STORY = URL + "/api/movie/";
    //电影评论： url +id+ "/0?"
    public static final String URL_MOVIE_COMMENT = URL + "/api/comment/praiseandtime/movie/";
    //轮播详情： url +id+ "?"
    public static final String URL_BANNER_DETAIL = URL + "/api/reading/carousel/";

}

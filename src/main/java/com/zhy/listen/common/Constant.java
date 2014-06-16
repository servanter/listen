package com.zhy.listen.common;

/**
 * 变量
 * 
 * @author zhanghongyan@outlook.com
 * 
 */
public class Constant {

    /**
     * 返回成功
     */
    public static String WEB_RETURN_SUCCESS = "00000";

    /**
     * 返回失败
     */
    public static String WEB_RETURN_ERROR = "11111";

    /**
     * 未绑定邮箱
     */
    public static String WEB_RETURN_NOTBIND_EMAIL = "10001";

    /**
     * 用户在session中的key
     */
    public static String WEB_USER_ALIAS = "user";

    /**
     * 第三方信息在session中的key
     */
    public static String WEB_USER_THIRD_ALIAS = "third";

    /**
     * 模板前缀名称
     */
    public static String TEMPLATE_LIBRA_PREWORDS = "libra.app.";

    /**
     * 动作前缀
     */
    public static String TEMPLATE_LIBRA_ACTION_PREWORDS = "libra.app.action.";

    /**
     * 模板中后缀成功名称
     */
    public static String TEMPLATE_SUCCESS = "success";

    /**
     * 模板中后缀失败名称
     */
    public static String TEMPLATE_ERROR = "error";

    /**
     * 评论默认名称
     */
    public static String TEMPLATE_COMMENT_DETAULTNAME_WORDS = "libra.app.comment.defaultname";

    /**
     * 评论默认头像
     */
    public static String TEMPLATE_COMMENT_DETAULTPIC_WORDS = "libra.app.comment.defaultpic";

    /**
     * 订阅标题
     */
    public static String TEMPLATE_SUBSCRIBE_TITLE_WORDS = "libra.app.subscribe.title";

    /**
     * 订阅默认内容话术
     */
    public static String TEMPLATE_SUBSCRIBE_DETAULTCONTENT_WORDS = "libra.app.subscribe.content";

    /**
     * memcache地址
     */
    public static String TEMPLATE_MEMCACHE_SERVER_WORDS = "libra.memcached.servers";

    /**
     * 根据关键字取地图的前缀
     */
    public static String TEMPLATE_MAP_KEYS_WORDS = "libra.app.map.getStaticMapByKeyWord";

    /**
     * 根据两者地址取地图的前缀
     */
    public static String TEMPLATE_MAP_TWOPATH_WORDS = "libra.app.map.getStaticMapByTwoPath";

    /**
     * 根据经纬度取地图的前缀
     */
    public static String TEMPLATE_MAP_WEIGHTANDHEIGHT_WORDS = "libra.app.map.getStaticMapByWeightAndHeight";

    /**
     * lucene 查询结果(无)前缀
     */
    public static String TEMPLATE_LUCENE_SEARCH_HASRESULT_WORDS = "libra.app.lucene.search.hasresult";

    /**
     * lucene 查询结果(有)前缀
     */
    public static String TEMPLATE_LUCENE_SEARCH_NORESULT_WORDS = "libra.app.lucene.search.noresult";

    /**
     * lucene 地址前缀
     */
    public static String TEMPLATE_LUCENE_PATH_WORDS = "libra.app.lucene.path";

    /**
     * 回调地址前缀
     */
    public static String TEMPLATE_THIRD_CALLBACK_WORDS = "libra.third.callback.url";
    
    /**
     * 投票详情地址前缀
     */
    public static String TEMPLATE_THIRD_INFODETAIL_WORDS = "libra.third.info.url";

    /**
     * 邀请话术前缀
     */
    public static String TEMPLATE_THIRD_MESSAGE_WORDS = "libra.third.info.url";

    /**
     * @ 前缀
     */
    public static String TEMPLATE_THIRD_NOTICE_USERPRE_WORDS = "libra.third.info.user.pre";

    /**
     * 访客默认大小
     */
    public static int VISITOR_DEFAULTSIZE = 9;
}

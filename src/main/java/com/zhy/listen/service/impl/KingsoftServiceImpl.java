package com.zhy.listen.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.common.Constant;
import com.zhy.listen.service.KingsoftService;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.util.MD5Util;
import com.zhy.listen.util.RandomUtils;

/**
 * 
 * 授权金山快盘 NOTE:
 * 
 * <pre>
 *   Step1. 先获取request token
 *   Step2. 进入授权页面
 *   Step3. post数据授权完成
 *   Step4. 获取accesstoken
 * @author zhanghongyan
 * 
 */
@Service
public class KingsoftServiceImpl implements KingsoftService {

    private static final String GET_METHOD = "GET";

    private static final String POST_METHOD = "POST";

    private static final String SPLIT = "&";

    public static Logger logger = Logger.getLogger(KingsoftServiceImpl.class);

    @Autowired
    private TemplateService templateService;

    @Override
    public void upload(Music music) {
        try {
            long time = System.currentTimeMillis();
            String requestUrl = templateService.getMessage(Constant.KINGSOFT_REQUEST_TOKEN_URL);
            String t = String.valueOf(time).substring(0, 10);
            String r = RandomUtils.generateString(6);
            Map<String, String> m = new TreeMap<String, String>(new TreeMapComparator());
            m.put("oauth_nonce", r);
            m.put("oauth_timestamp", t);
            m.put("oauth_consumer_key", templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY));
            m.put("oauth_signature_method", "HMAC-SHA1");
            m.put("oauth_version", "1.0");
            String sign = calculateSignature(m, requestUrl, GET_METHOD);

            String url = templateService.getMessage(Constant.KINGSOFT_REQUEST_URL, r, t,
                    templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY), sign);
            logger.info("[Request url]:" + url);

            GetMethod requestTokenMethod = new GetMethod(url);
            HttpClient client = new HttpClient();
            client.executeMethod(requestTokenMethod);
            String text = requestTokenMethod.getResponseBodyAsString();
            if (text != null && text.length() > 0) {
                JSONObject jsonObject = JSONObject.fromObject(text);
                if (jsonObject.containsKey("msg")) {

                    // 请求失败
                } else {

                    // request token & secret
                    String requestToken = jsonObject.getString("oauth_token");
                    String requestSecret = jsonObject.getString("oauth_token_secret");
                    String authURL = templateService.getMessage(Constant.KINGSOFT_AUTH_URL, requestToken);
                    GetMethod authMethod = new GetMethod(authURL);
                    authMethod.setRequestHeader("Host", "www.kuaipan.cn");
                    authMethod
                            .setRequestHeader("User-Agent",
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
                    client = new HttpClient();
                    client.executeMethod(authMethod);
                    String re = authMethod.getResponseBodyAsString();
                    Document document = Jsoup.parse(re);

                    // random s
                    String val = document.getElementsByTag("input").get(3).val();
                    String username = templateService.getMessage(Constant.KINGSOFT_USERNAME);
                    String userpwd = templateService.getMessage(Constant.KINGSOFT_PASSWORD);
                    String appName = templateService.getMessage(Constant.KINGSOFT_APPNAME);
                    PostMethod postMethod = new PostMethod(
                            templateService.getMessage(Constant.KINGSOFT_AUTHORISE_POST_URL));
                    postMethod.setRequestBody(new NameValuePair[] { new NameValuePair("username", username),
                            new NameValuePair("userpwd", userpwd), new NameValuePair("s", val),
                            new NameValuePair("app_name", appName), new NameValuePair("oauth_token", requestToken) });
                    postMethod.setRequestHeader("Referer", authURL);
                    client.executeMethod(postMethod);
                    String authResult = postMethod.getResponseBodyAsString();

                    // step3

                    r = RandomUtils.generateString(6);
                    time = System.currentTimeMillis();
                    t = String.valueOf(time).substring(0, 10);
                    m = new TreeMap<String, String>(new TreeMapComparator());
                    m.put("oauth_nonce", r);
                    m.put("oauth_timestamp", t);
                    m.put("oauth_consumer_key", templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY));
                    m.put("oauth_signature_method", "HMAC-SHA1");
                    m.put("oauth_version", "1.0");
                    m.put("oauth_secret", requestSecret);
                    m.put("oauth_token", requestToken);
                    String accessTokenSign = calculateSignature(m, "https://openapi.kuaipan.cn/open/accessToken", GET_METHOD);
                    String string = "https://openapi.kuaipan.cn/open/accessToken?oauth_consumer_key="
                            + templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY)
                            + "&oauth_signature_method=HMAC-SHA1&oauth_signature=" + accessTokenSign
                            + "&oauth_timestamp=" + t + "&oauth_nonce=" + r + "&oauth_version=1.0&oauth_token=" + requestToken;
                    logger.debug("[AccessToen url]:" + string);
                    GetMethod mm = new GetMethod(string);
                    client = new HttpClient();
                    client.executeMethod(mm);
                    String aa = mm.getResponseBodyAsString();
                    System.out.println(aa);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算signautre
     * 
     * @param m
     * @return
     * @throws UnsupportedEncodingException
     */
    private String calculateSignature(Map<String, String> m, String requestUrl, String method)
            throws UnsupportedEncodingException {
        String rowSign = "";
        for (Entry<String, String> entry : m.entrySet()) {
            if(entry.getKey().equals("oauth_secret")) {
                continue;
            }
            rowSign += entry.getKey() + "=" + entry.getValue() + SPLIT;
        }
        rowSign = rowSign.substring(0, rowSign.length() - 1);
        rowSign = URLEncoder.encode(rowSign, "utf-8");
        String signature = method + "&" + URLEncoder.encode(requestUrl, "utf-8") + SPLIT + rowSign;
        String key = templateService.getMessage(Constant.KINGSOFT_CONSUME_SECRET) + SPLIT;
        if (m.containsKey("oauth_secret")) {
            key += m.get("oauth_secret");
        }
        System.out.println(signature);
        String sign = MD5Util.hmacsha1(signature, key);
        sign = URLEncoder.encode(sign, "UTF-8");
        return sign;
    }

}

class TreeMapComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }

}

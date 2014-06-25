package com.zhy.listen.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.entity.mime.MultipartEntity;
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
                    String accessTokenSign = calculateSignature(m, "https://openapi.kuaipan.cn/open/accessToken",
                            GET_METHOD);
                    String string = "https://openapi.kuaipan.cn/open/accessToken?oauth_consumer_key="
                            + templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY)
                            + "&oauth_signature_method=HMAC-SHA1&oauth_signature=" + accessTokenSign
                            + "&oauth_timestamp=" + t + "&oauth_nonce=" + r + "&oauth_version=1.0&oauth_token="
                            + requestToken;
                    logger.debug("[AccessToken url]:" + string);
                    GetMethod mm = new GetMethod(string);
                    client = new HttpClient();
                    client.executeMethod(mm);
                    String accessResult = mm.getResponseBodyAsString();
                    JSONObject accessJson = JSONObject.fromObject(accessResult);
                    String accessToken = accessJson.getString("oauth_token");
                    String accessSecret = accessJson.getString("oauth_token_secret");

                    // step4
                    r = RandomUtils.generateString(6);
                    time = System.currentTimeMillis();
                    t = String.valueOf(time).substring(0, 10);
                    m = new TreeMap<String, String>(new TreeMapComparator());
                    m.put("oauth_nonce", r);
                    m.put("oauth_timestamp", t);
                    m.put("oauth_consumer_key", templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY));
                    m.put("oauth_signature_method", "HMAC-SHA1");
                    m.put("oauth_version", "1.0");
                    m.put("oauth_secret", accessSecret);
                    m.put("oauth_token", accessToken);

                    String uploadSign = calculateSignature(m,
                            "http://api-content.dfs.kuaipan.cn/1/fileops/upload_locate", GET_METHOD);
                    String uploadFileUrl = "http://api-content.dfs.kuaipan.cn/1/fileops/upload_locate?oauth_consumer_key="
                            + templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY)
                            + "&oauth_signature_method=HMAC-SHA1&oauth_signature="
                            + uploadSign
                            + "&oauth_timestamp="
                            + t + "&oauth_nonce=" + r + "&oauth_version=1.0&oauth_token=" + accessToken;
                    GetMethod uploadFileMethod = new GetMethod(uploadFileUrl);
                    HttpClient uploadHttp = new HttpClient();
                    uploadHttp.executeMethod(uploadFileMethod);
                    String uploadResult = uploadFileMethod.getResponseBodyAsString();
                    System.out.println(uploadResult);

                    
                    String uploadUrl = JSONObject.fromObject(uploadResult).getString("url");

                    r = RandomUtils.generateString(6);
                    time = System.currentTimeMillis();
                    t = String.valueOf(time).substring(0, 10);
                    m = new TreeMap<String, String>(new TreeMapComparator());
                    m.put("oauth_nonce", r);
                    m.put("oauth_timestamp", t);
                    m.put("oauth_consumer_key", templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY));
                    m.put("oauth_signature_method", "HMAC-SHA1");
                    m.put("oauth_version", "1.0");
                    m.put("oauth_secret", accessSecret);
                    m.put("oauth_token", accessToken);
                    m.put("overwrite", "True");
                    m.put("root", "kuaipan");
                    m.put("path", "/aaa.jpg");
                    String string2 = uploadUrl + "1/fileops/upload_file";
                    String uploadStep2Sign = calculateSignature(m, string2, POST_METHOD);
                    System.out.println("++++++++++" + string2);
                    File targetFile = new File("D:\\aaa.jpg");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
                    String result = "";
                    String every = null;
                    while((every = bufferedReader.readLine()) != null) {
                        result += every;
                    }
                    
                        
                    PostMethod uploadStep2Method = new PostMethod(string2);
                    NameValuePair[] parameters = new NameValuePair[] {
                            new NameValuePair("oauth_nonce", r),
                            new NameValuePair("oauth_timestamp", t),
                            new NameValuePair("oauth_consumer_key",
                                    templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY)),
                            new NameValuePair("oauth_signature_method", "HMAC-SHA1"),
                            new NameValuePair("oauth_version", "1.0"),
                            new NameValuePair("oauth_secret", accessSecret),
                            new NameValuePair("oauth_token", accessToken), new NameValuePair("overwrite", "True"),
                            new NameValuePair("root", "kuaipan"), new NameValuePair("path", "/aaa.jpg") ,
                            new NameValuePair("oauth_signature", uploadStep2Sign)};
                    uploadStep2Method.setRequestBody(parameters);
//                    uploadStep2Method.setRequestHeader("Accept-Encoding", "identity");
//                    uploadStep2Method.setRequestHeader("Host", "ufaclient.wps.cn");
//                    uploadStep2Method.setRequestHeader("Connection", "close");
                    uploadStep2Method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
                    uploadStep2Method.setRequestHeader("Referer", "http://www.surroundlife.com");
                    
                    
                    
                    FilePart fp = new FilePart(targetFile.getName(), targetFile, "image/jpeg", "UTF-8");
                    
                    Part[] parts =new Part[] { fp,new StringPart("oauth_nonce", r),
                            new StringPart("oauth_timestamp", t),
                            new StringPart("oauth_consumer_key",
                                    templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY)),
                            new StringPart("oauth_signature_method", "HMAC-SHA1"),
                            new StringPart("oauth_version", "1.0"),
                            new StringPart("oauth_secret", accessSecret),
                            new StringPart("oauth_token", accessToken), new StringPart("overwrite", "True"),
                            new StringPart("root", "kuaipan"), new StringPart("path", "/aaa.jpg") ,
                            new StringPart("oauth_signature", uploadStep2Sign) };
                    MultipartRequestEntity entity = new MultipartRequestEntity(parts, uploadStep2Method.getParams());
                    System.out.println("++++++++++++++++++++" + entity.getContentType() + "   " + entity.getContentLength());
                    uploadStep2Method.setRequestHeader("Content-Type", "multipart/form-data; boundary=----------TwaYUyulTQswqDWGHTE0ZF6Jy3NL94Yj");
                    uploadStep2Method.setRequestHeader("Content-Length", entity.getContentLength() + "");
                    uploadStep2Method.setRequestEntity(entity);
                    HttpClient upload2Http = new HttpClient();
                    upload2Http.executeMethod(uploadStep2Method);
                    System.out.println(uploadStep2Method.getResponseBodyAsString());

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
            if (entry.getKey().equals("oauth_secret")) {
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

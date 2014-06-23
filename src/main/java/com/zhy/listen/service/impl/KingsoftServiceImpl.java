package com.zhy.listen.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhy.listen.bean.Music;
import com.zhy.listen.common.Constant;
import com.zhy.listen.service.KingsoftService;
import com.zhy.listen.template.TemplateService;
import com.zhy.listen.util.MD5Util;
import com.zhy.listen.util.RandomUtils;

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
            String method = GET_METHOD + SPLIT;
            String rowSign = "";
            String requestUrl = templateService.getMessage(Constant.KINGSOFT_REQUEST_TOKEN_URL);
            TreeMap<String, String> m = new TreeMap<String, String>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            String t = String.valueOf(time).substring(0, 10);
            String r = RandomUtils.generateString(6);
            m.put("oauth_nonce", r);
            m.put("oauth_timestamp", t);
            m.put("oauth_consumer_key", templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY));
            m.put("oauth_signature_method", "HMAC-SHA1");
            m.put("oauth_version", "1.0");
            for (Entry<String, String> entry : m.entrySet()) {
                rowSign += entry.getKey() + "=" + entry.getValue() + SPLIT;
            }
            rowSign = rowSign.substring(0, rowSign.length() - 1);
            String mm = URLEncoder.encode(rowSign, "utf-8");
            mm = method + URLEncoder.encode(requestUrl, "utf-8") + SPLIT + mm;
            String sign = MD5Util.hmacsha1(mm, templateService.getMessage(Constant.KINGSOFT_CONSUME_SECRET) + SPLIT);
            System.out.println(sign);
            sign = URLEncoder.encode(sign, "UTF-8");
            String url = templateService.getMessage(Constant.KINGSOFT_REQUEST_URL, r, t, templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY), sign);
            logger.info("[Request url]:" + url);
//            URLConnection urlConnection = new URL(url).openConnection();
//            InputStream is = urlConnection.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);  
//            BufferedReader br = new BufferedReader(isr);  
//            String s = br.readLine();  
//            System.out.println(s);  
            
            GetMethod getMethod = new GetMethod(url);
            HttpClient client = new HttpClient();
            client.executeMethod(getMethod);
            String text = getMethod.getResponseBodyAsString();
            if(text != null && text.length() > 0) {
                JSONObject jsonObject = JSONObject.fromObject(text);
                if(jsonObject.containsKey("msg")) {
                    
                    // 请求失败
                } else {
                    String requestToken = jsonObject.getString("oauth_token");
                    String requestSecret = jsonObject.getString("oauth_token_secret");
                    
                    Connection conn = Jsoup.connect(templateService.getMessage(Constant.KINGSOFT_AUTH_URL , requestToken)).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36").method(Method.POST);
                    conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    conn.header("Accept-Encoding", "gzip,deflate,sdch");
                    conn.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
                    conn.header("Cache-Control", "max-age=0");
                    conn.header("Content-Type", "application/x-www-form-urlencoded");
                    conn.header("Connection", "keep-alive");
                    conn.header("Host", "www.kuaipan.cn");
                    conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
                    Document document = conn.post();
                    
                    
                    
//                    Document document = Jsoup.connect(templateService.getMessage(Constant.KINGSOFT_AUTH_URL , requestToken)).get();
                    String val = document.getElementsByTag("input").get(3).val();
                    
                    
//                    Connection connection = Jsoup.connect("https://www.kuaipan.cn/api.php?ac=open&op=authorisecheck").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36").method(Method.POST);
//                    connection.data("s", val);
//                    connection.data("app_name", "听听");
//                    connection.data("oauth_token", requestToken);
//                    connection.data("username", "fengshang@126.com");
//                    connection.data("userpwd", "8158011");
//                    Document dd = connection.post();
                    
                    String username = "fengshang@126.com";
                    String userpwd = "8158011";
                    String appName = "听听";
                    PostMethod postMethod = new PostMethod("https://www.kuaipan.cn/api.php?ac=open&op=authorisecheck");
                    postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("username", username), new NameValuePair("userpwd", userpwd), new NameValuePair("s", val), new NameValuePair("app_name", appName), new NameValuePair("oauth_token", requestToken)});
                    postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    postMethod.setRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
                    postMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
                    postMethod.setRequestHeader("Cache-Control", "max-age=0");
                    postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    postMethod.setRequestHeader("Connection", "keep-alive");
                    postMethod.setRequestHeader("Content-Length", String.valueOf(username.length() + userpwd.length() + appName.length() + val.length() + requestToken.length()));
                    postMethod.setRequestHeader("Host", "www.kuaipan.cn");
                    postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
                    
                    
                    
                    HttpClient client2 = new HttpClient();
                    client2.executeMethod(postMethod);
                    String authResult = postMethod.getResponseBodyAsString();
                    String str = new String(authResult.getBytes("ISO-8859-1"), "utf-8");
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

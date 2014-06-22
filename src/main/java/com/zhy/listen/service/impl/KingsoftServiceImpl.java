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
            System.out.println(URLEncoder.encode("=", "utf-8"));
            rowSign = rowSign.substring(0, rowSign.length() - 1);
            String mm = URLEncoder.encode(rowSign, "utf-8");
            mm = method + URLEncoder.encode(requestUrl, "utf-8") + SPLIT + mm;
            String sign = MD5Util.hmacsha1(mm, templateService.getMessage(Constant.KINGSOFT_CONSUME_SECRET) + SPLIT);
            System.out.println(sign);
            sign = URLEncoder.encode(sign, "UTF-8");
            String url = templateService.getMessage(Constant.KINGSOFT_REQUEST_URL, r, t, templateService.getMessage(Constant.KINGSOFT_CONSUME_KEY), sign);
            
            
            URLConnection urlConnection = new URL(url).openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);  
            BufferedReader br = new BufferedReader(isr);  
            String s = br.readLine();  
            System.out.println(s);  
            
            GetMethod getMethod = new GetMethod(url);
            HttpClient client = new HttpClient();
            client.executeMethod(getMethod);
            String text = getMethod.getResponseBodyAsString();
            if(text != null && text.length() > 0) {
                JSONObject jsonObject = JSONObject.fromObject(text);
                if(jsonObject.containsKey("msg")) {
                    
                    // 请求失败
                } else {
                    String requestToken = jsonObject.getString("oauth_token_secret");
                    String requestSecret = jsonObject.getString("oauth_token");
                    
                    
                    Document document = Jsoup.connect(templateService.getMessage(Constant.KINGSOFT_AUTH_URL , requestToken)).get();
                    String val = document.getElementsByTag("input").get(0).attr("value");
                    PostMethod postMethod = new PostMethod("https://www.kuaipan.cn/api.php?ac=open&op=authorisecheck");
                    postMethod.setRequestBody(new NameValuePair[]{new NameValuePair("s", val), new NameValuePair("app_name", "听听"), new NameValuePair("oauth_token", requestToken)});
                    HttpClient client2 = new HttpClient();
                    client2.executeMethod(postMethod);
                    String authResult = postMethod.getResponseBodyAsString();
                    
                    System.out.println(authResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.zhy.listen.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class MD5Util {

    private static final String HMAC_SHA1 = "HmacSHA1";

    public static String generateMD5Str(String str, String key) {
        String result = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF8"));
            byte[] temp;
            temp = messageDigest.digest(key.getBytes("UTF8"));
            for (int i = 0; i < temp.length; i++) {
                result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String hmacsha1(String data, String key) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
        }
        String oauth = new BASE64Encoder().encode(byteHMAC);
        return oauth;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        long time = System.currentTimeMillis();

        String method = "GET&";
        String rowSign = "";
        String requestUrl = "https://openapi.kuaipan.cn/open/requestToken";

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
        m.put("oauth_consumer_key", "xc3LW9mvoz1mhej7");
        m.put("oauth_signature_method", "HMAC-SHA1");
        m.put("oauth_version", "1.0");
        for (Entry<String, String>  entry : m.entrySet()) {
            rowSign += entry.getKey() + "=" + entry.getValue() + "&";
        }
        rowSign = rowSign.substring(0, rowSign.length() - 1);
        System.out.println(rowSign);
        String mm = URLEncoder.encode(rowSign, "utf-8");
        mm = method + URLEncoder.encode(requestUrl, "utf-8") + "&"+ mm;
        System.out.println(mm);
        String sign = "";
        sign = MD5Util.hmacsha1(mm, "7GoDieWLXFI9IpEU&");
        System.out.println(sign);

        String url = "https://openapi.kuaipan.cn/open/requestToken?oauth_nonce=" + r + "&oauth_timestamp=" + t
                + "&oauth_consumer_key=xc3LW9mvoz1mhej7" + "&oauth_signature_method=HMAC-SHA1&oauth_version=1.0" + "&oauth_signature=" + sign;
        System.out.println(url);

        
    }
}

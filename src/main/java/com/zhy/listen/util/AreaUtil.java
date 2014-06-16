package com.zhy.listen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AreaUtil {

    public static Logger logger = Logger.getLogger(AreaUtil.class);

    private static JSONArray sinaProvinces = new JSONArray();

    private static boolean isLoad = false;

    static {
        if (!isLoad) {
            synchronized (sinaProvinces) {
                loadSinaAreaData();
            }
        }
    }

    private synchronized static void loadSinaAreaData() {
        try {
            logger.info("[AreaUtil]: Loading sina area data....");
            InputStream inputStream = AreaUtil.class.getClassLoader()
                    .getResourceAsStream("com/zhy/libra/util/city.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String cityTemp = null;

            while ((cityTemp = bufferedReader.readLine()) != null) {
                builder.append(cityTemp);
            }

            JSONObject object = JSONObject.fromObject(builder.toString());
            sinaProvinces = object.getJSONArray("provinces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Sina省份数据获取省份名称
     * 
     * @return
     */
    public static String[] getSinaArea(String province, String city) {
        String[] result = new String[2];
        loop: for (int i = 0; i < sinaProvinces.size(); i++) {
            if (((JSONObject) sinaProvinces.get(i)).get("id").toString().equals(province)) {
                result[0] = ((JSONObject) sinaProvinces.get(i)).get("name").toString();
                JSONArray array = ((JSONObject) sinaProvinces.get(i)).getJSONArray("citys");
                for (int j = 0; j < array.size(); j++) {
                    JSONObject everyCity = ((JSONObject) array.get(j));
                    if (everyCity.containsKey(city)) {
                        result[1] = everyCity.get(city).toString();
                        break loop;
                    }
                }

            }
        }
        return result;
    }

}

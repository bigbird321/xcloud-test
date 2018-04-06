package net.machtalk.xcloud;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import net.machtalk.xcloud.utils.DateUtils;
import net.machtalk.xcloud.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 15-9-23
 * Time: 下午2:45
 */
public class Test extends TestCase {

    public void test(){
        System.out.println("run TestCase...");
        /*String did = "YA1000000000001";
        String key = "155452132222";
        String result = "彩灯";
        String report = "{\"cmd\":\"report\",\"count\":1,\"did\":\""+ did +"\",\"key\":\""+ key +"\",\"mid\":\""+ key +"\",\"product\":\""+ result +"\"}";
        JSONObject json = JSONObject.parseObject(report);
        System.out.println(report);
        System.out.println(json.toJSONString());
        System.out.println(json.getString("count"));
        System.out.println(isInRange(0,0,150));
        System.out.println(isInRange(10,0,150));
        System.out.println(isInRange(150,0,150));
        System.out.println(isInRange(1550,0,150));
        System.out.println(calculateIAQI(51.036,0,50,0,100));*/

        String regexKey = "/v1.1/deviceTypes/{tid}/{pid}";
        String regex = regexKey.replaceAll("\\{\\w+\\}", "[a-zA-Z0-9.-]+");
        String key = "/v1.1/deviceTypes/369/-1";
        if (Pattern.matches(regex, key)) {
            System.out.println(key + " match " + regexKey);
        }  else {
            System.out.println(key + " not match " + regexKey);
        }

        Object aa = 55;
        Float dvalue  = Float.parseFloat("0.000");
        System.out.println(dvalue);

        String result = "{\"userId\":\"1222\"}";
        JSONObject json = new JSONObject();
        json.put("isOnline",false);
        int online = json.getBoolean("isOnline") == true ? 1 : 0;

        String time = DateUtils.format(DateUtils.getPreDay(new Date()), new SimpleDateFormat("yyyy-MM-dd"));
        String endTime = DateUtils.format(DateUtils.getPreDay(new Date(), 7), new SimpleDateFormat("yyyy-MM-dd"));

        System.out.println(time);
        System.out.println(endTime);

        StringBuilder body = new StringBuilder();
        body.append("{\"loginId\":\"").append(111111111);
        body.append("\",\"accType\":\"").append(21);
        body.append("\"}");
        System.out.println(body.toString());

        String ext = "{\"freq\":1,\"upgrade\":{\"MD5\":\"8606cb4474f9f2dfb9395f5af528fb12\",\"size\":84140,\"version\":\"1.0.0.5\"}}";
        JSONObject extJson = JSONObject.parseObject(ext);

        extJson.remove("upgrade");
        extJson.remove("freq");
        if (extJson.isEmpty()){
            System.out.println("dddd");
        }
        System.out.println(extJson.toJSONString());
        JSONObject extJson1 = JSONObject.parseObject("{}");
        //json.put("add",extJson);
        //freq参数 xx = 1,5,10,20,30 min  {“success”:1,“freq”:xx,”upgrade”:{“version”:xx,“size”:xx,“MD5”:xx}}
        json.putAll(extJson1);
        System.out.println(json.toJSONString());
        String version = "0.0.0.0";
        if (!StringUtil.isEmpty(version) && !version.equals(null)) {
            System.out.println("fffffffffff");
        }


    }

    public boolean isInRange1(double current, double min, double max) {
        return Math.max(min, current) == Math.min(current, max);
    }
    public boolean isInRange(double current, double min, double max) {
        return current > min && current <= max;
    }

    private double calculateIAQI(double val, int iaqilo, int iaqihi, int bplo, int bphi){
        double iaqi = 0;
        // 向上取整
        iaqi = ((((double)iaqihi - (double)iaqilo)/((double)bphi - (double)bplo)) * (val - (double)bplo) + (double)iaqilo);
        return iaqi;
    }

    private int calculateIAQI1(double val, int iaqilo, int iaqihi, int bplo, int bphi){
        int iaqi = 0;
        // 向上取整
        iaqi = (int) Math.ceil(((((double)iaqihi - (double)iaqilo)/((double)bphi - (double)bplo)) * (val - (double)bplo) + (double)iaqilo));
        return iaqi;
    }


}

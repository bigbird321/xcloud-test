package net.machtalk.xcloud;

import cn.zcyun.xcloud.utils.common.UUIDGen;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        String gid = "cacaca";
        String did = "111122222111111";
        String loginTime = "2018-01-01T00:00:00";
        Integer timestamp = 1111111;
        Integer online = 1;
        String serverIp = "";
        String clientIp = null;
        JSONObject as = new JSONObject();
        as.put("1",1);
        StringBuilder body = new StringBuilder();
        body.append("{\"requestId\":\"").append(UUIDGen.generate())
                .append("\",\"gid\":\"").append(gid)
                .append("\",\"did\":\"").append(did)
                .append("\",\"loginTime\":").append(loginTime)
                .append(",\"online\":").append(online)
                .append(",\"serverIp\":").append(serverIp)
                .append(",\"clientIp\":").append(clientIp)
                .append("}");


        System.out.println(body.toString());

        String data = "{\"data\":[{\"dvid\":2009,\"key\":\"2016-12-23T19:33:53\",\"timestamp\":1482492833040,\"value\":\"56\"},{\"dvid\":2009,\"key\":\"2016-12-23T19:33:51\",\"timestamp\":1482492831908,\"value\":\"57\"},{\"dvid\":2009,\"key\":\"2016-12-23T18:50:50\",\"timestamp\":1482490250067,\"value\":\"55\"},{\"dvid\":2009,\"key\":\"2016-12-23T18:25:24\",\"timestamp\":1482488724951,\"value\":\"53\"},{\"dvid\":2009,\"key\":\"2016-12-23T17:38:23\",\"timestamp\":1482485903983,\"value\":\"52\"},{\"dvid\":2009,\"key\":\"2016-12-23T16:51:18\",\"timestamp\":1482483078634,\"value\":\"50\"},{\"dvid\":2009,\"key\":\"2016-12-23T15:36:36\",\"timestamp\":1482478596796,\"value\":\"46\"},{\"dvid\":2009,\"key\":\"2016-12-23T15:36:35\",\"timestamp\":1482478595715,\"value\":\"47\"},{\"dvid\":2009,\"key\":\"2016-12-23T14:04:44\",\"timestamp\":1482473084393,\"value\":\"45\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:57:13\",\"timestamp\":1482472633265,\"value\":\"43\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:53:45\",\"timestamp\":1482472425404,\"value\":\"41\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:51:13\",\"timestamp\":1482472273454,\"value\":\"39\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:50:02\",\"timestamp\":1482472202270,\"value\":\"37\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:48:53\",\"timestamp\":1482472133271,\"value\":\"35\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:47:42\",\"timestamp\":1482472062272,\"value\":\"33\"},{\"dvid\":2009,\"key\":\"2016-12-23T13:46:33\",\"timestamp\":1482471993272,\"value\":\"31\"}],\"total\":16}";

        JSONArray datas = JSONObject.parseObject(data).getJSONArray("data");

        JSONObject result = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONArray maxArray = new JSONArray();
        JSONArray minArray = new JSONArray();
        JSONArray totalArray = new JSONArray();

        //整理数据  key格式：2016-06-06T01:00:00
        HashMap<String, List<Double>> valueMap = new LinkedHashMap();
        //初始化0-23点的数据
        for (int i = 0; i <= 19; i++) {
            String key = "2016-12-23" + "T" + StringUtils.leftPad(i + "", 2, "0") + ":00:00";
            valueMap.put(key, new ArrayList<Double>());
        }

        for (int i = 0; i < datas.size(); i++) {
            JSONObject idata = datas.getJSONObject(i);
            String key = idata.getString("key").split(":")[0] + ":00:00";
            Double value = 0.0;
            try {
                value = Double.parseDouble(idata.getString("value"));
            } catch (Exception e) {
                continue;
            }
            List<Double> hourValues = valueMap.get(key);
            if (hourValues == null) {
                hourValues = new ArrayList<Double>();
            }
            hourValues.add(value);
            valueMap.put(key, hourValues);
        }
        //遍历map，统计数据
        double max = 0;
        double min = 0;
        double total = 0;
        double num = 0;
        int i = 0;

        // key格式 2016-08-01T01:00:00
        Double temp = 0d;
        boolean firstFlag = false;//标记第一条不为0的数据

        for (Map.Entry<String, List<Double>> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            double value = getMaxValue(entry.getValue());
            // 当前消息的value减去上一个小时的value,则为当前小时的增量。
            Double increaseValue = 0d;

            if (i > 0){
                if (value > 0 && !firstFlag){
                    firstFlag = true;
                    double firstValue = getMinValue(entry.getValue());
                    increaseValue = value - firstValue + 2;
                    temp = value;
                } else {
                    if ((value - temp) <=0 ){ // 说明本次数据不对，增量为0
                        increaseValue = 0d;
                    } else {
                        increaseValue = value - temp;
                        temp = value;
                    }
                }
            } else { // 处理第一个数据
                if (value > 0 && !firstFlag){
                    firstFlag = true;
                    double firstValue = getMinValue(entry.getValue());
                    increaseValue = value - firstValue + 2;
                } else {
                    increaseValue = value;
                }
                temp = value;
            }

            JSONObject dataObject = new JSONObject();
            JSONArray idataArray = new JSONArray();
            JSONObject idataObject = null;
            idataObject = new JSONObject();
            idataObject.put("key", 2009);
            idataObject.put("value", increaseValue);
            idataArray.add(idataObject);
            dataObject.put("time", key);
            dataObject.put("data", idataArray);
            dataArray.add(dataObject);
            total += increaseValue;
            if (max < value) {
                max = value;
            }
            if (min > value) {
                min = value;
            }
            i++;
        }

        //返回最大、最小、总值
        maxArray.add(getKVObject(2009, max));
        minArray.add(getKVObject(2009, min));
        totalArray.add(getKVObject(2009, total));
       result.put("success", 1);
       result.put("data", dataArray);
    //result.put("max", maxArray);
    //result.put("min", minArray);
       result.put("total", totalArray);

        System.out.println( result.toJSONString());
    }

    private static double getMaxValue(List<Double> value) {
        double max = 0;
        for (Double d : value){
            if (max < d){
                max = d;
            }
        }
        return  max;
    }

    private static double getMinValue(List<Double> value) {
        double min = 0;
        if (value != null && value.size() > 0){
            min = value.get(0);
            for (Double d : value){
                if (min > d){
                    min = d;
                }
            }
        }
        return  min;
    }

    protected static JSONObject getKVObject(int k, double v) {
        JSONObject kvObject = new JSONObject();
        kvObject.put("key", k);
        kvObject.put("value", v);
        return kvObject;
    }
}

package net.machtalk.xcloud;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 16-6-4
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class MRTest extends TestCase {
    private HashMap<Integer, List<Float>> dvidMap = new HashMap<Integer, List<Float>>();
    private HashMap<Integer, Float> map = new HashMap<Integer, Float>();

    public void test(){
        String key = "YA00100000000001-2016-06-03T23:00:00";
        ArrayList<String> values = new ArrayList<String>();
        values.add("1,0.000");
        values.add("1,0.000");
        values.add("1,0.000");
        values.add("1,0.000");

        String fields[] = key.toString().split("-");
        if (fields.length != 4){
            return;
        }
        for (String val : values) {
            String[] vals = val.toString().split(",");
            if (vals.length != 2){
                continue;
            }
            Integer dvid  = Integer.parseInt(vals[0]);
            Float dvalue  = Float.parseFloat(vals[1]);
            if (dvidMap.containsKey(dvid)){
                dvidMap.get(dvid).add(dvalue);
            } else {
                List<Float> dvalueList = new ArrayList<Float>();
                dvalueList.add(dvalue);
                dvidMap.put(dvid, dvalueList);
            }
        }

        AirDeviceData airDeviceData = new AirDeviceData();
        // 处理day和hour day格式：2015-02-01    hour格式：2015-02-01 01:00:00
        String hour = fields[1] + "-"  + fields[2] + "-"  + fields[3];
        airDeviceData.setCreate_time(hour);
        airDeviceData.setDevice_code(fields[0]);
        //1:  air_pressure 气压  2：temp 温度  3：humi 湿度  4：wdangle 风向  5：ws 风速 6：pm2_5 pm2.5  7:pm10  8:co  9:NO2 10:SO2  11:O3  12:aqi  13:O3_8h  14:首要污染物
        for (Map.Entry<Integer, List<Float>> entry : dvidMap.entrySet()){
            List<Float> dvalueList = entry.getValue();
            int count = 0;
            float value = 0;
            for (Float dvalue:dvalueList){
                value += dvalue;
                count ++;
            }
            DecimalFormat df = new DecimalFormat("#.000"); //保留3位小数
            float averageValue = Float.valueOf(df.format(value/count));
            map.put(entry.getKey(),averageValue);
            switch (entry.getKey()){
                case 1:
                    airDeviceData.setAir_pressure(averageValue);
                    break;
                case 2:
                    airDeviceData.setTemp(averageValue);
                    break;
                case 3:
                    airDeviceData.setHumi(averageValue);
                    break;
                case 4:
                    airDeviceData.setWdangle(averageValue);
                    break;
                case 5:
                    airDeviceData.setWs(averageValue);
                    break;
                case 6:
                    airDeviceData.setPm2_5(averageValue);
                    break;
                case 7:
                    airDeviceData.setPm10(averageValue);
                    break;
                case 8:
                    airDeviceData.setCo(averageValue);
                    break;
                case 9:
                    airDeviceData.setNo2(averageValue);
                    break;
                case 10:
                    airDeviceData.setSo2(averageValue);
                    break;
                case 11:
                    airDeviceData.setO3(averageValue);
                    break;
                default:
            }
        }
        System.out.println("");
    }

}

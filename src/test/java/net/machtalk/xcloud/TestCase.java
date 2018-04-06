package net.machtalk.xcloud;

import com.alibaba.fastjson.JSONObject;
import net.machtalk.xcloud.utils.DateUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 15-9-23
 * Time: 下午2:45
 */
public class TestCase extends junit.framework.TestCase {

    public void test(){
        System.out.println("run TestCase...");
        String timestamp = DateUtils.format(new Date(), new SimpleDateFormat("yyyyMMddHHmmss"));
        System.out.println( "timestamp==" + timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        long minStamp = calendar.getTime().getTime();
        System.out.println(calendar.getTime() + "==" + minStamp);
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        long maxStamp = calendar.getTime().getTime();
        System.out.println(calendar.getTime() + "==" + maxStamp);


        String operation = "{'cmd':'opt','to':'" + 111 + "','dvid':" + 1 + ",'value':" + 111 + "}";
        System.out.println(operation);
        JSONObject opt = JSONObject.parseObject(operation);
        System.out.println(opt.toJSONString());
        double onlineDeviceRatio = 0;
        int onlineDevice = 5;
        int totalOnlineDevice = 178;
        onlineDeviceRatio = (double)onlineDevice/(double)totalOnlineDevice*100;
       // DecimalFormat df = new DecimalFormat("######0.00");
        DecimalFormat df = new DecimalFormat("#0");
        /*df.setMaximumFractionDigits(2);
        df.setGroupingSize(0)*/;
        //df.setRoundingMode(RoundingMode.FLOOR);
        System.out.println(df.format(onlineDeviceRatio));
        System.out.println(Double.valueOf(df.format(3332.855)));
        System.out.println(Double.valueOf(df.format(55/2)));
        System.out.println(Math.round(55.1/2));
        System.out.println((int)(55.66/2));
        long value = 99;
        int count = 4;
        double averageValue = Math.round((double)value/count);
        System.out.println(averageValue);

        String longtime = "1468480788123";

        String time = DateUtils.format(new Date(Long.parseLong(longtime)), new SimpleDateFormat("yyyyMMdd'T'HHmmssSSS"));
        String rowkey = time.split("T")[0] + "-" + "rrrrrrrrrrrr" + "-" + time.split("T")[1];

        System.out.println(rowkey);
        //ResponseUtil.sendJsonNoCache(response, result.toJSONString());

        System.out.println("/gome/data/report".startsWith("/open/"));
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

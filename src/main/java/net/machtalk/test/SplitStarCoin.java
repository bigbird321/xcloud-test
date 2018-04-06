package net.machtalk.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by zhaop on 2017/11/29
 */
public class SplitStarCoin {

    private SplitStarCoin(){}

    /**
     * @描述 随机分配v2.0<br>
     * @param total
     * @param size
     * @return
     * @author administrator
     * @版本 v1.0.0
     * @日期 2017-11-7
     */
    public static List<Double> roundSplit(Double total, Integer size){
        int len = 0;
        if(size>=1&&size<100){
            len = 2;
        }
        if(size>=100&&size<1000){
            len = 3;
        }
        if(size>=1000&&size<10000){
            len = 4;
        }
        if(size>=10000&&size<100000){
            len = 5;
        }
        if(size>=100000){
            len = 6;
        }

        Double avg = new BigDecimal(total.toString()).divide(new BigDecimal(size.toString()),len,BigDecimal.ROUND_DOWN).doubleValue();

        Double min = new BigDecimal(String.valueOf(avg)).multiply(new BigDecimal(String.valueOf("0.5"))).doubleValue();

        Double max = new BigDecimal(String.valueOf(avg)).multiply(new BigDecimal(String.valueOf("1.5"))).doubleValue();

        List<Double> values = new ArrayList<Double>();

        int order = 0;
        Double maxFree = 0d;
        Random random = new Random();
        for(int i=2;i<size;i++){
            Double randNum = 0d;
            if(order==0){
                while(randNum>max || randNum<avg){
                    randNum = random.nextDouble()%(max-min+1) + min;
                }

                maxFree =new BigDecimal(String.valueOf(randNum)).subtract(new BigDecimal(String.valueOf(avg))).doubleValue();
                randNum = new BigDecimal(Double.toString(randNum)).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();
                values.add(randNum);
                total = new BigDecimal(String.valueOf(total)).subtract(new BigDecimal(String.valueOf(randNum))).doubleValue();
                order= 1;
                continue;
            }
            if(order==1){
                randNum = new BigDecimal(String.valueOf(avg)).subtract(new BigDecimal(String.valueOf(maxFree))).doubleValue();
                randNum = new BigDecimal(Double.toString(randNum)).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();
                values.add(randNum);
                order= 0;
                total = new BigDecimal(String.valueOf(total)).subtract(new BigDecimal(String.valueOf(randNum))).doubleValue();
                continue;
            }
        }
        Double remain = new BigDecimal(String.valueOf(total)).divide(new BigDecimal(String.valueOf(2))).doubleValue();
        remain = new BigDecimal(Double.toString(remain)).setScale(len+1,BigDecimal.ROUND_HALF_UP).doubleValue();
        int index = random.nextInt(values.size());
        values.add(index, remain);
        remain = new BigDecimal(String.valueOf(total)).subtract(new BigDecimal(String.valueOf(remain))).doubleValue();
        index = random.nextInt(values.size());
        values.add(index, remain);
        return values;
    }


    /*public static List<Double> roundSplit_ghost2(Double total,Integer size){
        int len = 0;
        if(size>=1&&size<100){
            len = 2;
        }
        if(size>=100&&size<1000){
            len = 3;
        }
        if(size>=1000&&size<10000){
            len = 4;
        }
        if(size>=10000&&size<100000){
            len = 5;
        }
        if(size>=100000){
            len = 6;
        }

        Double avg = MatchUtils.divide(total, Double.parseDouble(String.valueOf(size)), len);
        avg = new BigDecimal(avg).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();

        Double min = MatchUtils.multiply(avg, 0.8d, len);
        min = new BigDecimal(min).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();

        Double max = MatchUtils.multiply(avg, 1.2d, len);
        max = new BigDecimal(max).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();

        List<Double> values = new ArrayList<Double>();

        int order = 0;
        Double maxFree = 0d;
        for(int i=0;i<size;i++){
            Double randNum = 0d;
            if(order==0){
                while(randNum>max || randNum<avg){
                    randNum = min + Math.random() * max % (max - min + 1);
                    randNum = new BigDecimal(randNum).setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                maxFree = MatchUtils.subtract(randNum, avg, len);
                values.add(randNum);
                total = MatchUtils.subtract(total, randNum, len);
                order= 1;
                continue;
            }
            if(order==1){
                randNum = MatchUtils.subtract(avg, maxFree, len);
                values.add(randNum);
                order= 0;
                total = MatchUtils.subtract(total, randNum, len);
                continue;
            }
        }
        return values;
    }*/


    /**
     * @描述 随机分配<br>
     * @param starCoin 总数
     * @param userCount 个数
     * @return 随机分配数组
     * @author administrator
     * @版本 v1.0.0
     * @日期 2017-8-7
     */
    public static List<Double> split(double starCoin,double userCount){

        List<Double> array = new ArrayList<Double>();
        Random r = new Random();
        double sum = 0;
        for (int i = 0; i < userCount; i++) {
            array.add(r.nextDouble()*starCoin + 0.01 * userCount * starCoin);
            sum += array.get(i);
        }
        for (int i = 0; i < array.size(); i++) {
            array.set(i, array.get(i) / sum*starCoin);
        }
        Collections.sort(array);

        for (int i = 0; i < array.size(); i++) {
            if(array.get(i)<=0.01){
                array.set(i, 0.01);
            }
            else if(i==array.size()-1){
                BigDecimal he =new BigDecimal("0");
                for(int j=0;j<array.size()-1;j++){
                    BigDecimal b =new BigDecimal(Double.toString(array.get(j)));
                    he=he.add(b);
                }
                BigDecimal moneyb =new BigDecimal(Double.toString(starCoin));
                array.set(i, moneyb.subtract(he).doubleValue());
            }
            else{
                array.set(i, (int)(array.get(i)*100)*1.0/100);
            }
        }
        Collections.shuffle(array);
        return array;
    }

    /**
     * @描述 <br>
     * @param total
     * @param size
     * @param min
     * @param len
     * @return
     * @author administrator
     * @版本 v1.0.0
     * @日期 2017-10-18
     */
    public static List<Double> roundSplit(double total, int size, double min,int len) {
        List<Double> values = new ArrayList<Double>();
        for (int i = 1; i < size; i++) {
            double safe_total = (total - (size - i) * min) / (size - i);
            double money = Math.random() * (safe_total - min) + min;
            BigDecimal money_bd = new BigDecimal(money);
            money = money_bd.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
            total = total - money;
            BigDecimal total_bd = new BigDecimal(total);
            total = total_bd.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
            values.add(money);
        }
        values.add(total);
        return values;
    }

    /**
     * @描述 <br>
     * @param total
     * @param size
     * @param min
     * @param len
     * @return
     * @author administrator
     * @版本 v1.0.0
     * @日期 2017-10-18
     */
    /*public static List<Double> roundSplit_ghost(double total, int size, double min,int len) {
        List<Double> values = new ArrayList<>();
        double area = MatchUtils.divide(total, Double.parseDouble(String.valueOf(size)), len);
        double bd2 = new BigDecimal(String.valueOf(area)).setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
        for(int i=0;i<size-1;i++){
            values.add(bd2);
        }
        double count = MatchUtils.multiply(bd2, Double.parseDouble(String.valueOf(size-1)), len);
        values.add(MatchUtils.subtract(total, count, len));
        return values;
    }*/

    public static void test() {
        long start = System.currentTimeMillis();
        //total是25~6.5之间的数值   size是人数 9000~1万
        Random random = new Random();

        //Double randNum = random.internalNextDouble(0.0001, 0.0002);

        List<Double> result = roundSplit(10d,9999);
        /*for (Double double1 : result) {
            System.out.println(double1);
        }*/
        System.out.println("cost:" + (System.currentTimeMillis() - start) + " ms");
    }

    public static void main(String[] args) {
        test();
    }
}

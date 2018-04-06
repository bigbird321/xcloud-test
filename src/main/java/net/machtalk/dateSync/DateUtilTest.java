package net.machtalk.dateSync;


import cn.zcyun.xcloud.utils.common.DateUtils;

/**
 * Created by zhaop on 2018/1/5
 */
public class DateUtilTest {
    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
                    System.out.println(this.getName()+":"+ DateUtils.parseFromFormats("2017-11-15"));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }


    public static void main(String[] args) {
        for(int i = 0; i < 1; i++){
            new TestSimpleDateFormatThreadSafe().start();
        }

    }

}

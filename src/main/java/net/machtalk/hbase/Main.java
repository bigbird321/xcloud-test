package net.machtalk.hbase;

import java.io.IOException;

/**
 * Created by zhaop on 2017/12/13
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HBaseClient.getHbaseClient().scanTsdb("tsdb");
    }
}

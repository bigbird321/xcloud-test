package net.machtalk.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by zhaop on 2017/12/9
 */
public class HBaseClient {
    private static final Logger logger = LoggerFactory.getLogger(HBaseClient.class);

    private static Configuration conf = null;
    private static HBaseClient hc = null;
    private static Connection conn = null;

    /**
     * 初始化配置
     */
    static {
        try {
            /*ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
            String quorum = resourceBundle.getString("hbase.zk.quorum");
            String clientPort = resourceBundle.getString("hbase.zk.clientPort");*/
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.0.177");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            //一次RPC请求的超时时间,默认该值为1min
            //conf.setInt("hbase.rpc.timeout",20000);
            //客户端发起一次数据操作直至得到响应之间总的超时时间
            //conf.setInt("hbase.client.operation.timeout",30000);
            //客户端发起一次scan操作的rpc调用至得到响应之间总的超时时间
            conf.setInt("hbase.client.scanner.timeout.period",600000);
            conn = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            logger.error("初始化Hbase异常，异常信息为：" + e.getMessage());
            logger.error(e.getMessage(),e);
        } catch (Throwable e){
            logger.error("初始化Hbase异常，异常信息为：" + e.getMessage(),e);
        }
    }

    public static HBaseClient getHbaseClient() {
        if (null == hc) {
            synchronized (HBaseClient.class) {
                if (null == hc) {
                    hc = new HBaseClient();
                }
            }
        }
        return hc;
    }

    /**
     * hellofit rowkey = 100000001000003050-9-20171108174752
     * 属性1 2 3 4 5为int，其余为String，写死
     * response: key:时间戳   dvid   value
     */
    public void scanTsdb(String tableName) throws IOException {
        long st = System.currentTimeMillis();

        Table table = null;
        ResultScanner scanner = null;
        try {
            Scan scan = new Scan();
            //scan.setStartRow(Bytes.toBytes(start));
            //scan.setStopRow(Bytes.toBytes(stop));
            //scan.setCaching(500000);
            scan.setCaching(1000);
            scan.setCacheBlocks(false);
            //指定最多返回的Cell数目。用于防止一行中有过多的数据，导致OutofMemory错误。
            //scan.setBatch(1000);
            //添加RowFilter
			/*Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
					new RegexStringComparator(".*" + "-" + dvid + "-" + did + "$"));*/

            /*Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                    new RegexStringComparator(".*" + "-" + year + "*$"));*/

            /*Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                    new SubstringComparator("-" + year));

            scan.setFilter(rowFilter);*/

            //MultiRowRangeFilter
            table = conn.getTable(TableName.valueOf(tableName));
            scanner = table.getScanner(scan);

            for (Result result : scanner) {
/*                System.out.println("获得到rowkey:" + Bytes.toString(result.getRow()));
                for (KeyValue keyValue : result.raw()) {
                    System.out.println("列：" + Bytes.toString(keyValue.getFamily())
                            + "====值:" + Bytes.toString(keyValue.getValue()));
                }*/

                List<Cell> cells = result.listCells();
                if (cells != null) {
                    Integer dvid = null;
                    String key = null;
                    String value = null;
                    for (Cell cell : cells) {
                        //BatchedDataPoints.doubleValue

                        String fieldName = new String(CellUtil.cloneQualifier(cell));

                        //Float fieldValue = Float.intBitsToFloat(Bytes.getInt(CellUtil.cloneValue(cell), 0));
                        Float fieldValue = Float.intBitsToFloat(Bytes.getInt(CellUtil.cloneValue(cell), 0));

                        logger.info("fieldName:{},fieldValue:{}", fieldName, fieldValue);


/*                        if (fieldName.equals("dvid")) {
                            dvid = Integer.parseInt(fieldValue);
                        } else if (fieldName.equals("key")) {
                            key = fieldValue;
                        } else if (fieldName.equals("value")) {
                            value = fieldValue;
                        }*/
                    }
                }
            }
        } finally {
            if (scanner != null){
                scanner.close();
            }
            if (table != null) {
                table.close();
            }
            conn.close();
        }
    }



}

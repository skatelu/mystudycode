package testjava;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;

public class ScanContentTest {

    String startTime = "2018-01-01";
    String stopTime = "2018-03-01";

    public static void main(String[] args) {
        Scan scan = new Scan();
        String regionCode = HBaseUtil.genRegionCode("", "201801", 6);
        scan.setRowPrefixFilter(Bytes.toBytes(regionCode+""));

    }

}

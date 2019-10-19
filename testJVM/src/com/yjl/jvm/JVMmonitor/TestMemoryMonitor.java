package com.yjl.jvm.JVMmonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试图形化界面监控内存变化
 * JVM 参数设置：-Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class TestMemoryMonitor {

    /**
     * 内存占位符对象，一个OOMObject大约占64KB
     */
    static class OOMObject{
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws Exception{
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            //延时操作，令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args)throws Exception {
        fillHeap(1000);
    }
}

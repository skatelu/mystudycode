package com.yjl.jvm.GCstaduy;

/**
 * 内存分配与回收策略
 * 这个默认是在Client 即缩点停止时间 用户交互模式下
 * 大对象直接进入老年代
 * VM 参数：-XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * -XX:+UseSerialGC ：虚拟机运行在Client模式下的默认值，Serial + serial Old 垃圾回收模式
 * -Xms20m : Java堆最小为20m
 * -Xmx20m ：java堆最大为20m
 * -Xmn10m ：将10m分配给新代
 * -XX:+PrintGCDetails ：收集器日志参数，告诉虚拟机在发生垃圾收集行为时打印内存回收日志，并且在进程退出的时候输出当前的内存各区域的分配情况
 * -XX:PretenureSizeThreshold ：令大于这个设置的对象直接在老年代中分配；这样的目的是避免在Eden区及两个Survivor区之间发生大量的内存复制
 * 注意：PretenureSizeThreshold这个参数只对Serial 和 parNew 两款收集器有效，Parallel Scavenge收集器不认识这个参数
 * Parallel Scavenge收集器一般不需要设置。如果遇到必须使用此参数场合，可以考虑ParNew 家 CMS 的收集器组合
 *
 */
public class PretenureSizeThreshold {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation;

        allocation = new byte[ 4 * _1MB];
    }

}

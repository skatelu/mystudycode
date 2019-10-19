package com.yjl.jvm.GCstaduy;

/**
 * 内存分配与回收策略
 * 这个默认是在Client 即缩点停止时间 用户交互模式下
 * 对象内存优先分配在Eden（即新生代区域）区
 * args ：-verbose:gc -XX:UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-UseAdaptiveSizePolicy
 * -XX:+UseSerialGC ：虚拟机运行在Client模式下的默认值，Serial + serial Old 垃圾回收模式
 * -Xms20m : Java堆最小为20m
 * -Xmx20m ：java堆最大为20m
 * -Xmn10m ：将10m分配给新代
 * -XX:+PrintGCDetails ：收集器日志参数，告诉虚拟机在发生垃圾收集行为时打印内存回收日志，并且在进程退出的时候输出当前
 * 的内存各区域的分配情况
 * -XX:SurvivorRatio=8 ：决定了新生代中Eden区与一个Survivor区的空间比利是8：1
 * -XX:-UseAdaptiveSizePolicy：禁用动态调整，是SurvivorRatio可以起作用
 */

public class Allocation {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1,allocation2,allocation3,allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

}

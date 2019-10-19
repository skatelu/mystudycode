package com.yjl.esper.context;

public class HashContextTest {

    // 以java的hashcode方法计算sid的值(sid必须大于5)，以CRC32算法计算tid的值，然后对10取余后的值来建立context
   String epl =  "create context HashPerson coalesce by hash_code(sid) from Student(sid>5), consistent_hash_crc32(tid) from Teacher granularity 10";


}


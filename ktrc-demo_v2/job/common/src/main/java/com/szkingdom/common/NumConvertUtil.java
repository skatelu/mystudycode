package com.szkingdom.common; /**
 * Created by admin on 2018/6/15.
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumConvertUtil {

    //百分比类型小数转整型，扩大10^8倍
    public static long rateToLong(double d) {
        if(d >= 92233720368.5) {
            //9223372036854775807最大数额
            return Math.round(d);
        } else {
            return Math.round(d * 100000000);
        }
    }
    public static double longToRate(long l) {
        long a = l / 100000000;
        long b = l % 100000000;
        double r = 0.00000001 * b + a;
        return r;
    }

    //金额类型小数转整型，扩大10^3倍
    public static long moneyToLong(double d) {
        if(d >= 9223372036854775.8) {
            //9223372036854775807最大数额
            return Math.round(d);
        } else {
            return Math.round(d * 1000);
        }
    }
    public static double longToMoney(long l) {
        long a = l / 1000;
        long b = l % 1000;
        double r = 0.001 * b + a;
        return r;
    }

    //价格类型小数转整型，扩大10^4倍
    public static long priceToLong(double d) {
        if(d >= 92233720368547758.1) {
            //9223372036854775807最大数额
            return Math.round(d);
        } else {
            return Math.round(d * 10000);
        }
    }
    public static double longToPrice(long l) {
        long a = l / 10000;
        long b = l % 10000;
        double r = 0.0001 * b + a;
        return r;
    }

    //float直转double可能精度错位，故封装类对象来实现。double转float则不必
    public static double float2double(float f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        double d = b.doubleValue();
        return d;
    }

    public static double round(double d, short precision) {
        //入参精度范围只允许设置1-8，超出则赋值为允许的边界值
        if (precision > 8){
            precision = 8;
        }
        if (precision < 1){
            precision = 1;
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);//设置四舍五入
        nf.setMinimumFractionDigits(precision);//设置最小保留几位小数
        nf.setMaximumFractionDigits(precision);//设置最大保留几位小数
        return Double.valueOf(nf.format(d));
    }
}

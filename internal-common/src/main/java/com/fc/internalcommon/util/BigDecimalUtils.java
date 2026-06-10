package com.fc.internalcommon.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 除法（保留两位小数，向上取整）
     * @param v1
     * @param v2
     * @return
     */
    public static double divide(int v1, int v2) {
        if (v2 <= 0) {
            throw new IllegalArgumentException("除数违法");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

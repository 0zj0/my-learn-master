package com.example.utils;

import java.math.BigDecimal;

/**
 * 经纬度工具
 * @author 刘洋印
 * @date 2018/6/23 15:36
 */
public class LngAndLatUtil {
    /**
     * 单位千米
     */
    private final static double EARTH_RADIUS = 6378.137;

    /**
     * 角度弧度计算公式 rad:().
     * 360度=2π π=Math.PI
     * x度 = x*π/360 弧度
     * @param degree 度
     * @return double
     * @author 刘洋印
     */
    private static double getRadian(double degree) {
        return degree * Math.PI / 180.0;
    }

    /**
     * 依据经纬度计算两点之间的距离 GetDistance:().
     * @param lng1 1经度
     * @param lat1 1纬度
     * @param lng2 2经度
     * @param lat2 2纬度
     * @return 距离 单位 米
     * @author 刘洋印
     */
    public static double getDistance(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
        double lngA = lng1 != null ? lng1.doubleValue() : 0;
        double latA = lat1 != null ? lat1.doubleValue() : 0;
        double lngB = lng2 != null ? lng2.doubleValue() : 0;
        double latB = lat2 != null ? lat2.doubleValue() : 0;
        double radLat1 = getRadian(latA);
        double radLat2 = getRadian(latB);
        // 两点纬度差
        double a = radLat1 - radLat2;
        // 两点的经度差
        double b = getRadian(lngA) - getRadian(lngB);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s * 1000;
    }
}

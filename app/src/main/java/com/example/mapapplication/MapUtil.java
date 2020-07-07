package com.example.mapapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

import static java.lang.Math.PI;

/**
 * @author 柳涛
 * 三方地图工具类
 */
public class MapUtil {

    private static final String TENCENT_KEY = "ST2BZ-C7GKX-J3I43-7FQJM-YRKVF-VPBJO";

    /**
     * 打开腾讯地图
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     * @param mode  mode = 1（公交）、2（驾车）、3（骑行）4（步行）. 默认:3
     */
    public static void openTencent(Context context, double dlat, double dlon, String dname, String mode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        double[] location = calBD09toGCJ02(dlat, dlon);
        intent.setData(Uri.parse("qqmap://map/routeplan?from=我的位置&fromcoord=CurrentLocation"
                + "&to=" + dname
                + "&tocoord=" + location[0] + "," + location[1]
                + "&type=" + MapMode.TencentMode.getTencentMode(mode)
                + "&referer=" + TENCENT_KEY));
        context.startActivity(intent);
    }

    /**
     * 打开高德
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     * @param mode  mode = 1（公交）、2（驾车）、3（骑行）4（步行）. 默认:3
     */
    public static void openGaoDeMap(Context context, double dlat, double dlon, String dname, String mode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        double[] location = calBD09toGCJ02(dlat, dlon);
        intent.setPackage("com.autonavi.minimap");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("amapuri://route/plan/?sourceApplication=" + R.string.app_name//todo 获取包名
                + "&sname=我的位置&dlat=" + location[0]
                + "&dlon=" + location[1]
                + "&dname=" + dname
                + "&dev=0&m=0&t="
                + MapMode.GaodeMode.getGaodeMode(mode)));
        context.startActivity(intent);
    }

    /**
     * 打开百度地图
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     * @param mode  mode = 1（公交）、2（驾车）、3（骑行）4（步行）. 默认:3
     */
    public static void openBaiduMap(Context context, double dlat, double dlon, String dname, String mode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String builder = "baidumap://map/direction?origin=我的位置&coord_type= bd09ll&destination=name:" +
                dname + "|latlng:" +
                dlat + "," + dlon + "&mode=" + MapMode.BaiduMode.getBaiduMode(mode);//todo "&src="获取包名
        intent.setData(Uri.parse(builder));
        context.startActivity(intent);
    }

    /**
     * 检测地图应用是否安装
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 百度坐标系 BD-09 to 火星坐标系 GCJ-02
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return [纬度, 经度]
     */
    public static double[] calBD09toGCJ02(double latitude, double longitude) {
        double x = longitude - 0.0065, y = latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        double retLat = z * Math.sin(theta);
        double retLon = z * Math.cos(theta);
        return new double[]{retLat, retLon};
    }
}


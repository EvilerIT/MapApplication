package com.example.mapapplication;

public enum MapEnum {
    TECENT("com.tencent.map", "腾讯地图"),
    GAODE("com.autonavi.minimap", "高德地图"),
    BAIDU("com.baidu.BaiduMap", "百度地图");
    public String packageName;
    public String name;

    MapEnum(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }
}

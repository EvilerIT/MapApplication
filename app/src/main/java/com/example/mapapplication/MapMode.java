package com.example.mapapplication;

import android.text.TextUtils;

/**
 * @author 柳涛
 * 支持的交通模式
 */
public class MapMode {
    // 对外暴露的交通模式
    public enum CommonMode {
        TRANSIT("1"),
        DRIVING("2"),
        RIDING("3"),
        WALKING("4");

        public String mode;

        CommonMode(String mode) {
            this.mode = mode;
        }
    }

    // 百度地图交通模式映射
    public enum BaiduMode {
        TRANSIT("transit", CommonMode.TRANSIT.mode),
        DRIVING("driving", CommonMode.DRIVING.mode),
        WALKING("walking", CommonMode.WALKING.mode),
        RIDING("riding", CommonMode.RIDING.mode);

        public String targetMode;
        public String mode;

        BaiduMode(String targetMode, String mode) {
            this.targetMode = targetMode;
            this.mode = mode;
        }

        public static String getBaiduMode(String mode) {
            for (BaiduMode baiduMode : BaiduMode.values()) {
                if (TextUtils.equals(mode, baiduMode.mode)) {
                    return baiduMode.targetMode;
                }
            }
            return BaiduMode.RIDING.targetMode;
        }
    }

    // 高德地图交通模式映射
    public enum GaodeMode {
        TRANSIT("1", CommonMode.TRANSIT.mode),
        DRIVING("0", CommonMode.DRIVING.mode),
        WALKING("2", CommonMode.WALKING.mode),
        RIDING("3", CommonMode.RIDING.mode);

        public String targetMode;
        public String mode;

        GaodeMode(String targetMode, String mode) {
            this.targetMode = targetMode;
            this.mode = mode;
        }

        public static String getGaodeMode(String mode) {
            for (GaodeMode gaodeMode : GaodeMode.values()) {
                if (TextUtils.equals(mode, gaodeMode.mode)) {
                    return gaodeMode.targetMode;
                }
            }
            return GaodeMode.RIDING.targetMode;
        }
    }

    // 腾讯地图交通模式映射
    public enum TencentMode {
        TRANSIT("bus", CommonMode.TRANSIT.mode),
        DRIVING("drive", CommonMode.DRIVING.mode),
        WALKING("walk", CommonMode.WALKING.mode),
        RIDING("bike", CommonMode.RIDING.mode);

        public String targetMode;
        public String mode;

        TencentMode(String targetMode, String mode) {
            this.targetMode = targetMode;
            this.mode = mode;
        }

        public static String getTencentMode(String mode) {
            for (TencentMode tencentMode : TencentMode.values()) {
                if (TextUtils.equals(mode, tencentMode.mode)) {
                    return tencentMode.targetMode;
                }
            }
            return TencentMode.RIDING.targetMode;
        }
    }
}

package com.example.mapapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author 柳涛
 * 三方地图打开弹窗
 */
public class MapPopupWindow extends PopupWindow {
    private View view;
    private Context context;
    private double latitude;
    private double longitude;
    private String destination;
    private String mode;

    private MapPopupWindow(Context context, double latitude, double longitude, String destination, String mode) {
        super(context);
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destination = TextUtils.isEmpty(destination) ? "目标地址" : destination;
        this.mode = TextUtils.isEmpty(mode) ? MapMode.CommonMode.RIDING.mode : mode;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(context).inflate(R.layout.map_pop_window_layout, null);
        initView();
        this.setContentView(view);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
    }

    private void initView() {
        LinearLayout container = view.findViewById(R.id.container);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        container.setDividerDrawable(drawable);
        drawable.setColor(Color.parseColor("#F8F8F8"));
        container.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE |
                LinearLayout.SHOW_DIVIDER_END | LinearLayout.SHOW_DIVIDER_BEGINNING);
        for (MapEnum mapEnum : MapEnum.values()) {
            if (MapUtil.checkMapAppsIsExist(context, mapEnum.packageName)) {
                TextView textView = new TextView(context);
                textView.setText(mapEnum.name);
                textView.setMaxLines(1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                textView.setTextColor(Color.parseColor("#222222"));
                textView.setPadding(0, 24, 0, 24);
                textView.setGravity(Gravity.CENTER);
                container.addView(textView);
                addClickListener(textView, mapEnum.name);
            }
        }
        view.findViewById(R.id.cancel_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MapPopupWindow.this.isShowing()) {
                    MapPopupWindow.this.dismiss();
                }
            }
        });
    }

    private void addClickListener(TextView textView, final String name) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(MapEnum.BAIDU.name, name)) {
                    MapUtil.openBaiduMap(context, latitude, longitude, destination, mode);
                } else if (TextUtils.equals(MapEnum.TECENT.name, name)) {
                    MapUtil.openTencent(context, latitude, longitude, destination, mode);
                } else {
                    MapUtil.openGaoDeMap(context, latitude, longitude, destination, mode);
                }
            }
        });
    }

    public void show(View anchorView) {
        this.setAnimationStyle(R.style.pop_animation);
        this.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        bgAlpha(0.5f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        bgAlpha(1.0f);
    }

    private void bgAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = alpha;// 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public static final class Builder {
        private Activity activity;
        private double latitude;
        private double longitude;
        private String destination;
        private String mode;

        public Builder(double latitude, double longitude, Activity activity) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.activity = activity;
        }

        public Builder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder setMode(String mode) {
            this.mode = mode;
            return this;
        }

        public MapPopupWindow build() {
            return new MapPopupWindow(activity, latitude, longitude, destination, mode);
        }
    }
}

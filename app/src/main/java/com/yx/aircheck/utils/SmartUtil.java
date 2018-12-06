package com.yx.aircheck.utils;

import android.content.Context;
import android.util.TypedValue;

public class SmartUtil {
    public static int dip2px(int dp, Context context) {

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}

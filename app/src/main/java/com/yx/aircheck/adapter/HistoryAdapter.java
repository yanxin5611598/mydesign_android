package com.yx.aircheck.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.yx.aircheck.bean.CheckData;

import java.util.List;
import java.util.Map;

public class HistoryAdapter extends SimpleAdapter{
    public HistoryAdapter(Context context, List<? extends Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}

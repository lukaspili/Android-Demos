package com.siu.android.demos.adapter;

import android.widget.TextView;
import com.siu.android.andutils.adapter.SimpleViewHolder;
import com.siu.android.demos.R;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoViewHolder extends SimpleViewHolder {

    TextView textView;

    @Override
    public void init() {
        textView = (TextView) row.findViewById(R.id.text);
    }
}

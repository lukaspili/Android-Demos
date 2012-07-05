package com.siu.android.demos.adapter;

import android.content.Context;
import com.siu.android.andutils.adapter.SimpleAdapter;
import com.siu.android.demos.R;
import com.siu.android.demos.model.DemoModel;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoAdapter extends SimpleAdapter<DemoModel, DemoViewHolder> {

    public DemoAdapter(Context context, List<DemoModel> list) {
        super(context, R.layout.row, list);
    }

    @Override
    protected void configure(DemoViewHolder demoViewHolder, DemoModel demoModel) {
        demoViewHolder.textView.setText(demoModel.getName());
    }

    @Override
    protected DemoViewHolder createViewHolder() {
        return new DemoViewHolder();
    }
}

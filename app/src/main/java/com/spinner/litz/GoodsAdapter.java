package com.spinner.litz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zt.bw.R;

/**
 * Created by 鍧� on 2016/9/5.
 */
public class GoodsAdapter extends ArrayAdapter<GoodsInfo> {

    private final LayoutInflater mInflater;

    public GoodsAdapter(Context context) {
        super(context, R.layout.bluetooth_device_list_item);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bluetooth_device_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.device_name);
//        TextView info = (TextView) convertView.findViewById(R.id.device_info);
        GoodsInfo info = getItem(position);
        name.setText(info.toString());
        return convertView;
    }
}

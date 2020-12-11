package com.zt.bw.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zt.bw.R;

public class DialogUtil {
    public static Dialog createSettingDialog(Context context, String info, final OnComfirmListening listener) {
        LayoutInflater inflaterDl = LayoutInflater.from(context);
        View parent = inflaterDl.inflate(R.layout.dialog_setting, null);
        EditText etUrl = (EditText) parent.findViewById(R.id.et_url);
        if (!TextUtils.isEmpty(info))
            etUrl.setText(info);
        final Dialog dialog = new Dialog(context, R.style.mDialogStyle_black);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (width * 0.5), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(parent, params);
        TextView confirmBt = (TextView) parent.findViewById(R.id.tv_confirm);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString();
                if (listener != null && !TextUtils.isEmpty(url)) {
                    listener.confirm(url);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
    public interface OnComfirmListening {
        void confirm(String data);
    }
    public static void dismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}

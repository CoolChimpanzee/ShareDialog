package com.jarvan.sharedialog;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jarvan on 2019/2/19.
 * Toast辅助类
 */
public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(Context context, String s) {
        if (toast == null) {
            if (context == null) {
                return;
            }
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            try {
                LinearLayout linearLayout = (LinearLayout) toast.getView();
                TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                messageTextView.setTextSize(11);
            } catch (Exception e) {
                e.printStackTrace();
            }
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (!s.equals(oldMsg)) {
                if (twoTime - oneTime > 3000) {
                    toast.setText(s);
                    toast.show();
                    oneTime = twoTime;
                }
            }
        }

    }


    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }


}

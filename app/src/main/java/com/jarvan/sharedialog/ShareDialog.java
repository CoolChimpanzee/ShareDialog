package com.jarvan.sharedialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import com.jarvan.sharedialog.R;

import utils.BottomShareAnimator;

/**
 * 自定义底部弹出分享带动画效果对话框
 * Created by mustang on 2019/3/25.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {


    public static final String NORMAL = "normal";
    public static final String MOREITEMS = "unNormal";
    private final Context context;
    private final String type;
    private Handler mHandler = new Handler();
    private View view;
    private HorizontalScrollView scrollView;
    private View blurring_view;


    public ShareDialog(Context context, String type) {
        super(context, R.style.shareDialog);
        this.context = context;
        this.type = type;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.bottom_share_dialog, null);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        scrollView = (HorizontalScrollView) view.findViewById(R.id.hsv);
        blurring_view = view.findViewById(R.id.blurring_view);
        findViewById(R.id.cancel).setOnClickListener(this);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    //如果触动屏幕就执行
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        View view = ((HorizontalScrollView) v).getChildAt(0);
                        //判断是否滑动到最右边了，如果是，就让blurring_view隐藏，否则显示
                        if (view.getMeasuredWidth() <= v.getScrollX() + v.getWidth() + 2) {
                            blurring_view.setVisibility(View.INVISIBLE);
                        } else {
                            blurring_view.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;

                }
                return false;

            }
        });
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lin);
        if (type.equals(NORMAL)) {
            linearLayout.getChildAt(4).setVisibility(View.GONE);
            linearLayout.removeViewAt(4);
        }


    }

    @Override
    public void show() {
        super.show();
        showAnimation();
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = scrollView.getChildAt(0);
                //判断是否滑动栏到底了，如果是，就让iv这个图片隐藏，否则显示
                if (view.getMeasuredWidth() <= scrollView.getWidth() + 2) {
                    blurring_view.setVisibility(View.INVISIBLE);
                } else {
                    blurring_view.setVisibility(View.VISIBLE);
                }
            }
        }, 100);


    }

    private void showAnimation() {
        try {
            final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lin);
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(view, "translationY", 1200, 0);
            fadeAnim.setDuration(400);
            fadeAnim.start();
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(ShareDialog.this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 800, 0);
                        fadeAnim.setDuration(200);
                        BottomShareAnimator kickAnimator = new BottomShareAnimator();
                        kickAnimator.setDuration(150);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }
                }, i * 50 + 400);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void dismiss() {
        view.clearAnimation();
        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(view, "translationY", 0, 1000);
        fadeAnim.setDuration(400);
        fadeAnim.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShareDialog.super.dismiss();
            }
        }, 300);

    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (isShowing()) {
            dismiss();
        }
        switch (v.getId()) {
            case R.id.cancel:
                break;
            default:
                break;
        }

    }


}

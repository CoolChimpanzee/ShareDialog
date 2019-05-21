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
import android.widget.TextView;


/**
 * 自定义底部弹出分享带动画效果对话框
 * Created by mustang on 2019/3/25.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private Handler mHandler = new Handler();
    private View view;
    private ObservableHorizontalScrollView scrollView;
    private View blurring_view;
    private ShareBean shareBean;
    private LinearLayout linearLayout;


    public ShareDialog(Context context, ShareBean shareBean) {
        super(context, R.style.shareDialog);
        this.context = context;
        this.shareBean = shareBean;

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
        scrollView = (ObservableHorizontalScrollView) view.findViewById(R.id.hsv);
        blurring_view = view.findViewById(R.id.blurring_view);
        findViewById(R.id.cancel).setOnClickListener(this);
        linearLayout = (LinearLayout) view.findViewById(R.id.lin);
        scrollView.setScrollViewListener(new ObservableHorizontalScrollView.ScrollViewListener() {
            //滑动监听,获取图片
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y,
                                        int oldx, int oldy) {
                int scrollX = scrollView.getScrollX();
                int width = scrollView.getWidth();
                int scrollViewMeasuredWidth = scrollView.getChildAt(0).getMeasuredWidth();
                if ((scrollX + width) >= scrollViewMeasuredWidth) {
                    //System.out.println("滑动到了底部 scrollY=" + scrollX + "height=" + width + "scrollViewMeasuredHeight=" + scrollViewMeasuredWidth);
                    blurring_view.setVisibility(View.INVISIBLE);
                } else {
                    blurring_view.setVisibility(View.VISIBLE);
                }

            }

        });



        if (shareBean.isShowMenu()) {
            //H5返回的弹窗分享
            if (shareBean.getShareWay() != null && shareBean.getShareWay().length > 0) {
                linearLayout.removeAllViews();
                for (int i = 0; i < shareBean.getShareWay().length; i++) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.bottom_share_item, null);
                    textView.setPadding(dip2px(13f), dip2px(13f), dip2px(13f), dip2px(13f));
                    switch (shareBean.getShareWay()[i]) {
                        case "WeChatFriends":
                            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.share_weixin_icon, 0, 0);
                            textView.setText("微信好友");
                            linearLayout.addView(textView, i);
                            break;
                        case "WeChatMoments":
                            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.share_pyq_icon, 0, 0);
                            textView.setText("朋友圈");
                            linearLayout.addView(textView, i);
                            break;
                        case "AlipayFriends":
                            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.zfb_icon, 0, 0);
                            textView.setText("支付宝好友");
                            linearLayout.addView(textView, i);
                            break;
                        case "AlipayMoments":
                            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.zfb_life_icon, 0, 0);
                            textView.setText("支付宝动态");
                            linearLayout.addView(textView, i);
                            break;
                        case "Clipboard":
                            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.copy_icon, 0, 0);
                            textView.setText("复制链接");
                            linearLayout.addView(textView, i);
                            break;
                        default:
                            break;
                    }


                }

            } else {
                //默认的弹窗分享
            }
        } else {
            //无弹窗单个分享

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
                //判断是否滑动栏到底了，如果是，就让blurring_view隐藏，否则显示
                if (view.getMeasuredWidth() <= scrollView.getWidth() + 22) {
                    blurring_view.setVisibility(View.INVISIBLE);
                } else {
                    blurring_view.setVisibility(View.VISIBLE);
                }
            }
        }, 100);


    }

    private void showAnimation() {
        try {
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
        switch (((TextView) v).getText().toString()) {
            case "微信好友":
                ToastUtils.showToast(context, "微信好友");
                break;
            case "朋友圈":
                ToastUtils.showToast(context, "朋友圈");
                break;
            case "支付宝好友":
                ToastUtils.showToast(context, "支付宝好友");
                break;
            case "支付宝动态":
                ToastUtils.showToast(context, "支付宝动态");
                break;
            case "复制链接":
                ToastUtils.showToast(context, "复制链接");
                break;
            default:
                break;
        }


    }

    private int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

package com.jarvan.sharedialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void share(View view) {
        ShareBean shareBean = new ShareBean();
        shareBean.setImageArray(new String[]{"http://youimg1.c-ctrip.com/target/fd/tg/g1/M03/F8/52/CghzflWnreWAHBPlAAMJ6ZRBi2I611.jpg"});
        shareBean.setShareUrl("https://www.i3ni.cn");
        shareBean.setShareContent("I’m a Java Developer with an enduring interest in Coding and PUBG");
        shareBean.setShareTitle("爱尚你");
        shareBean.setShowMenu(true);
        shareBean.setShareWay(new String[]{"WeChatFriends","WeChatMoments","AlipayFriends","AlipayMoments","Clipboard"});
        shareBean.setContentType("");
        ShareDialog shareDialog = new ShareDialog(this,shareBean);
        shareDialog.show();
    }
}

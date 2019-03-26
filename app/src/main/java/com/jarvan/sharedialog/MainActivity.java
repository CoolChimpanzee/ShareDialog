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
        ShareDialog shareDialog = new ShareDialog(this,ShareDialog.MOREITEMS);
        shareDialog.show();
    }
}

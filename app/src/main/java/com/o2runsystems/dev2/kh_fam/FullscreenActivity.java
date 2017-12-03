package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class FullscreenActivity extends BaseActivity {

    TextView txt_reseved;
    Typeface font1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        init();
        fonts();
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            Intent mainIntent = new Intent(FullscreenActivity.this,Login_activity.class);
            FullscreenActivity.this.startActivity(mainIntent);
            FullscreenActivity.this.finish();

        }, 2000);

        //asdas

    }


    void fonts(){
        font1 = Typeface.createFromAsset(getAssets() ,"fonts/DroidKufiRegular.ttf");

        txt_reseved.setTypeface(font1);

    }

    void init(){

        txt_reseved = (TextView) findViewById(R.id.txt_reseved);

    }




}

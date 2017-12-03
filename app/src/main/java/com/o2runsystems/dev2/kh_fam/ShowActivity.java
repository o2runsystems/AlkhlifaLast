package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import WebServices.Models.UrlInfo;

public class ShowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);
        TextView user= (TextView) findViewById(R.id.txt1);
        TextView time= (TextView) findViewById(R.id.txt2);
        TextView titel= (TextView) findViewById(R.id.txt3);
        TextView body= (TextView) findViewById(R.id.txt4);
        ImageView avatar= (ImageView) findViewById(R.id.avater_showactiviy);
        ImageView image= (ImageView) findViewById(R.id.imgbody_showactiviy);
        // get intent
        Intent intent = this.getIntent();
        String u =    intent.getExtras().getString("user");
        String t =    intent.getExtras().getString("time");
        String ti =    intent.getExtras().getString("titel");
        String b =    intent.getExtras().getString("body");
        String a =            intent.getExtras().getString("avatar");
        String i=       intent.getExtras().getString("image");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarShow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("التفــاصيل");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        //upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        user.setText(u);
        time.setText(t);
        titel.setText(ti);
        body.setText(b);
        String url = UrlInfo.DownloadUrl+ a +".Jpg";
        Picasso.with(this).load(url).placeholder(R.drawable.avatar).into(avatar);

        final String url2 = UrlInfo.DownloadUrl+ i +".Jpg";

        Picasso.with(this).load(url2).into(image);


        image.setOnClickListener(view -> {
            // zooming
            Intent en =new Intent(ShowActivity.this ,imagezoom.class);
            en.putExtra("imgurl",url2);
            startActivity(en);


        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

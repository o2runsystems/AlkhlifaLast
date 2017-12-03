package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import WebServices.Models.UrlInfo;

public class show_market extends BaseActivity {

    ImageView market_img;
    TextView market_name , market_about , market_price , market_cat , market_type , market_time ,market_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_market);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarShowmarket);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("التفــاصيل");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
       // upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        market_img = (ImageView)  findViewById(R.id.market_img);
        market_name = (TextView) findViewById(R.id.market_name);
        market_about = (TextView)  findViewById(R.id.market_about);
        market_price = (TextView)  findViewById(R.id.market_price);
        market_cat = (TextView)  findViewById(R.id.market_cat);
        market_type = (TextView)  findViewById(R.id.market_type);
        market_time = (TextView)  findViewById(R.id.market_time);
        market_phone = (TextView)  findViewById(R.id.market_phone);

        Intent intent = this.getIntent();
        String img=       intent.getExtras().getString("img");
        String name =    intent.getExtras().getString("name");
        String price =    intent.getExtras().getString("price");
        String about =    intent.getExtras().getString("about");
        String cat =    intent.getExtras().getString("cat");
        String type =   intent.getExtras().getString("type");
        String time =   intent.getExtras().getString("time");
        String phone = intent.getExtras().getString("phone");

        market_name.setText(name);
        market_price.setText(price);
        market_about.setText(about);
        market_cat.setText(cat);
        market_type.setText(type);
        market_time.setText(time);
        market_phone.setText(phone);
        final String url = UrlInfo.DownloadUrl+ img +".Jpg";
        Picasso.with(this).load(url).placeholder(R.drawable.market).into(market_img);

        market_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // zooming
                Intent en =new Intent(show_market.this ,imagezoom.class);
                en.putExtra("imgurl",url);
                startActivity(en);
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import WebServices.Models.UrlInfo;

public class imglibdetials extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglibdetials);
       Intent en = getIntent();
        TextView name = (TextView) findViewById(R.id.libimgdetTxtName);
        ImageView imgview = (ImageView) findViewById(R.id.libimgdetImgPhoto);
        TextView txtinfo = (TextView) findViewById(R.id.libimgdetinfo);
        name.setText("الاسـم : "+en.getStringExtra("name"));
        final String str = UrlInfo.DownloadUrl+ en.getStringExtra("imgurl") +".Jpg";

        imgview.setOnClickListener(view -> {
            Intent en1 = new Intent(imglibdetials.this , imagezoom.class);
            en1.putExtra("imgurl" , str);
            startActivity(en1);
        });
        Picasso.with(this).load(str).placeholder(R.drawable.plholder).into(imgview);
        txtinfo.setText(en.getStringExtra("info"));

    }
}

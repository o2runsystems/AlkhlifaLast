package com.o2runsystems.dev2.kh_fam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class imagezoom extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagezoom);
       String url = getIntent().getStringExtra("imgurl");
        ImageView imgview = (ImageView) findViewById(R.id.imgzoom);
        Picasso.with(this).load(url).into(imgview);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgview);
        photoViewAttacher.update();

    }
}

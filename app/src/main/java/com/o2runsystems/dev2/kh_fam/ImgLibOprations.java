package com.o2runsystems.dev2.kh_fam;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.databinding.ActivityAddImagelibBinding;
import com.o2runsystems.dev2.kh_fam.databinding.ActivityImgLibOprationsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import Utiles.Compress;
import WebServices.Api.ImgLibServices;
import WebServices.Api.UploadServices;
import WebServices.Models.ImgLibModel;
import WebServices.Models.UrlInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImgLibOprations extends BaseActivity {
    public boolean IsUpdate;
    ActivityAddImagelibBinding imglibbinding;
    String imgurl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imglibbinding = DataBindingUtil.setContentView(this , R.layout.activity_add_imagelib);

        IsUpdate = getIntent().getBooleanExtra("IsUpdate" , false);

        imglibbinding.bookfab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 1);
        });
        imglibbinding.btnBack.setOnClickListener(view -> {
            finish();
        });

        imglibbinding.btnSignup.setOnClickListener(view -> {
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlInfo.AppUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ImgLibServices services = retrofit.create(ImgLibServices.class);


            ImgLibModel model = new ImgLibModel();
            model.imgurl = imgurl;
            model.name = imglibbinding.txtforname.getText().toString();
            model.sumrize = imglibbinding.txteditForFamous.getText().toString();
         Call<ResponseBody> restlts = services.InsertNewImgLib(model);
            restlts.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){

                        Toast.makeText(ImgLibOprations.this , R.string.new_pic , Toast.LENGTH_LONG).show();

                        Intent intent = new Intent();

                        int  id = 0;
                        try {
                            id = Integer.valueOf(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        intent.putExtra("id" , id);
                        intent.putExtra("imgurl",imgurl);
                        intent.putExtra("name",imglibbinding.txtforname.getText().toString());
                        intent.putExtra("sumrizing" , imglibbinding.txteditForFamous.getText().toString());
                        setResult(Activity.RESULT_OK , intent);

                        finish();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ImgLibOprations.this , "Oprations Not Complete !" , Toast.LENGTH_LONG).show();
                }
            });
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);
                imglibbinding.circleImageView.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
                Gson gson = new GsonBuilder()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlInfo.MeduaServerUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                UploadServices services = retrofit.create(UploadServices.class);
                File file = new File(NameAndUUid[1]);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("upload" , file.getName() , reqFile);
                Call<ResponseBody> body = services.UploadFile(part);
                body.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ImgLibOprations.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ImgLibOprations.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ImgLibOprations.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });
                imgurl = NameAndUUid[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

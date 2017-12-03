package com.o2runsystems.dev2.kh_fam;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2runsystems.dev2.kh_fam.databinding.ActivityAddbooklibBinding;

import java.io.File;
import java.io.IOException;

import Utiles.Compress;
import WebServices.Api.BookLibServices;
import WebServices.Api.UploadServices;
import WebServices.Models.BookLibModel;
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
 // add any coomint

public class addbooklib extends BaseActivity {
    ActivityAddbooklibBinding booklibBinding;
    String imgid = null;
    public boolean IsUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        booklibBinding = DataBindingUtil.setContentView(this , R.layout.activity_addbooklib);
        IsUpdate = getIntent().getBooleanExtra("IsUpdate" , false);
        booklibBinding.bookfab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 1);
        });


        booklibBinding.btnSignup.setOnClickListener(view -> {
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlInfo.AppUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            BookLibServices services = retrofit.create(BookLibServices.class);
            BookLibModel model = new BookLibModel();
            model.author = booklibBinding.bookAurthor.getText().toString();
            model.bookDescrib = booklibBinding.bookDecrip.getText().toString();
            model.downloadurl = booklibBinding.bookUrlDowoload.getText().toString();
            model.imgId = imgid;
            model.name = booklibBinding.inputName.getText().toString();

            Call<ResponseBody> restlt = services.InsertNewBookLib(model);
            restlt.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(addbooklib.this , R.string.new_pic , Toast.LENGTH_LONG).show();

                        Intent intent =new Intent();
                        int id=0;
                        try {
                            id = Integer.valueOf(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("id",id);
                        intent.putExtra("author" , booklibBinding.bookAurthor.getText().toString());
                        intent.putExtra("bookDescrib" ,booklibBinding.bookDecrip.getText().toString());
                        intent.putExtra("downloadurl", booklibBinding.bookUrlDowoload.getText().toString());
                        intent.putExtra("imgId",imgid);
                        intent.putExtra("name",booklibBinding.inputName.getText().toString());
                        setResult(Activity.RESULT_OK , intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(addbooklib.this , "Oprations Not Complete !" , Toast.LENGTH_LONG).show();
                }
            });
        });
        booklibBinding.btnBack.setOnClickListener(view -> {
            finish();
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);
                booklibBinding.bookimg.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
                Gson gson = new GsonBuilder().create();

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlInfo.MeduaServerUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
                UploadServices services = retrofit.create(UploadServices.class);
                File file = new File(NameAndUUid[1]);
                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("upload" , file.getName() , reqFile);
                Call<ResponseBody> body = services.UploadFile(part);
                body.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(addbooklib.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(addbooklib.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(addbooklib.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });
                imgid = NameAndUUid[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.databinding.ActivityMarketPuplisherBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.Presenters.MarketAddPostPresnter;
import PublishFeature.Presenters.MarketAddPostViewReqired;
import Utiles.Compress;
import WebServices.Api.UploadServices;
import WebServices.Models.UrlInfo;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import WebServices.Models.user_response;

public class MarketActivityPuplisher extends BaseActivity implements MarketAddPostViewReqired {


    ActivityMarketPuplisherBinding marketActivity;
    String username, avater,phone ;
    Realm realms;
    MarketAddPostPresnter presnter;
    String imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         presnter = new MarketAddPostPresnter(this);
        Init();

        marketActivity.savebtnmarket.setOnClickListener(view -> {

            final MarketModel post = new MarketModel();
            post.img = imgid;
            post.name = username;
            post.about = marketActivity.bodytxtmarkets.getText().toString();
            post.price = marketActivity.txtPricemarket.getText().toString();
            post.cat = marketActivity.cattxtmarket.getText().toString();
            post.type =  marketActivity.typetxtmarket.getText().toString();
            post.time = new Date();
            post.phone = phone;
            presnter.AddMraketPost(post);


        });

    }


       void Init(){
        realms = Realm.getDefaultInstance();
        marketActivity = DataBindingUtil.setContentView(this , R.layout.activity_market_puplisher);
        getintent();

        marketActivity.fabmarket.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 1);
        });
    }

       void getintent(){
           user_response dataof = realms.where(user_response.class).findFirst();
      username = dataof.fname + " " + dataof.ffName +" "  + dataof.fffName +" " + dataof.ffffName +" " ;
     avater = realms.where(user_response.class).findFirst().picId;
      phone = realms.where(user_response.class).findFirst().mobile;
}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);
                marketActivity.imgmarket.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
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
                            Toast.makeText(MarketActivityPuplisher.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MarketActivityPuplisher.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MarketActivityPuplisher.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });
                imgid = NameAndUUid[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presnter.Ondestroy();

    }

    @Override
    public void CloseActivity() {
        finish();
    }

    @Override
    public void ShowToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

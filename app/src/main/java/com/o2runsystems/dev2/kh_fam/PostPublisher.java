package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.databinding.ActivityPostPublisherBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import PublishFeature.Presenters.AddPublishPresenter;
import PublishFeature.Presenters.AddPublisherReqirdView;
import Utiles.Compress;
import PublishFeature.PojoClasses.Publishs;
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

public class PostPublisher extends BaseActivity implements AddPublisherReqirdView {

    String avater;
    int type;
    String username;
    Realm realms;
    ActivityPostPublisherBinding postactivity;

    String imgid;
    AddPublishPresenter _addpresnter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        _addpresnter = new AddPublishPresenter(this);

        Inint();

        postactivity.btnSignup.setOnClickListener(view -> {

            final Date date=  new Date();
            final Publishs post = new Publishs();
            post.Title = postactivity.txtforname.getText().toString();
            post.Body = postactivity.txtbody.getText().toString();
            post.Images = imgid;
            post.UserName  = username;
            post.DateTime = date;
            post.Type = type;
            post.Avatar = avater;
            _addpresnter.AddPublisher(post);
        });
        postactivity.btnBack.setOnClickListener(view -> {
            finish();
        });


    }

    void getintet(){

     Intent intent = this.getIntent();
        avater = realms.where(user_response.class).findFirst().picId;
        type = intent.getIntExtra("type",1);

        user_response data = realms.where(user_response.class).findFirst();

        username = data.fname + " " + data.ffName + " " + data.fffName;
    }

    void  Inint(){
        realms = Realm.getDefaultInstance();
        getintet();

        postactivity = DataBindingUtil.setContentView(this , R.layout.activity_post_publisher);
        postactivity.txtfornamecard.setText("");
        postactivity.txtbody.setText("");



        postactivity.bookfab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);
                postactivity.img.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
                postactivity.img.setVisibility(View.VISIBLE);
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
                            Toast.makeText(PostPublisher.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(PostPublisher.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(PostPublisher.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
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
        _addpresnter.Ondestroy();
    }

    @Override
    public void CloseActivity() {
        finish();
    }

    @Override
    public void ShowTost(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

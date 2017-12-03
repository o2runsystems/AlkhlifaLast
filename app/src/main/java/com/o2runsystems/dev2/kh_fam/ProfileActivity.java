package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import Utiles.Compress;
import WebServices.Api.MemoService;
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

public class ProfileActivity extends BaseActivity {
    AppCompatButton btn_signup;
    String error;
    String imgidofprofile;
    EditText input_password2,input_password1, input_name, input_phone , numberofkid , job , ocp;
    ImageView input_image;
     Realm realms;
    String  e ;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        realms = Realm.getDefaultInstance();

        initlizeing();
        getintent();
        signup();
    }

    void initlizeing(){
       e = getResources().getString(R.string.error_field_required);

        btn_signup = (AppCompatButton) findViewById(R.id.btn_signup);
        input_password1 = (EditText)findViewById(R.id.input_password1);
        input_password2 = (EditText)findViewById(R.id.input_password2);
        input_name = (EditText)findViewById(R.id.input_name);
        input_phone = (EditText)findViewById(R.id.input_phone);
        input_image = (ImageView)findViewById(R.id.circleImageView);
        numberofkid =  (EditText)findViewById(R.id.numberofkidoprofile);
        job =  (EditText)findViewById(R.id.jobnameprfile);
        ocp =  (EditText)findViewById(R.id.jobnameprfile);


        input_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent
                        , 1);
            }
        });
        error= getResources().getString(R.string.error_incorrect_password);
    }
    String imgid;
    void getintent(){
        id = realms.where(user_response.class).findFirst().id;
        input_password1.setText(realms.where(user_response.class).findFirst().Pass);
        input_password2.setText(realms.where(user_response.class).findFirst().Pass);
        user_response data = realms.where(user_response.class).findFirst();
        input_name.setText( data.fname + " " + data.ffName + " " +data.fffName + " " +data.ffffName);

        input_phone.setText(realms.where(user_response.class).findFirst().mobile);

        numberofkid.setText(realms.where(user_response.class).findFirst().numberOfKids);

        job.setText(realms.where(user_response.class).findFirst().job);

        ocp.setText(realms.where(user_response.class).findFirst().compony);

        imgid = realms.where(user_response.class).findFirst().picId;
        String url = UrlInfo.DownloadUrl+ imgid +".Jpg";
        Picasso.with(this).load(url).placeholder(R.drawable.avatar).into(input_image);
    }

    void signup(){
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true){
                        Gson gsoninsert = new GsonBuilder().create();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(UrlInfo.AppUrl).addConverterFactory(GsonConverterFactory.create(gsoninsert))
                                .build();
                        MemoService serviceinsert = retrofit.create(MemoService.class);
                        final String pass= input_password2.getText().toString();
                        final String phone = input_phone.getText().toString();

                    final Integer numberofkids = Integer.valueOf(numberofkid.getText().toString());

                    final String jobs = job.getText().toString();

                    final String ocps = ocp.getText().toString();


                        Call<ResponseBody> add = serviceinsert.updateuser(id,pass,phone , imgidofprofile, numberofkids , jobs , ocps);

                        add.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //int StatusCode = response.code();
                                try {
                                     String s = response.body().string();

                                     if (s.equals("true")){
                                         Toast.makeText(ProfileActivity.this , R.string.updateuser, Toast.LENGTH_LONG).show();
                                         realms.executeTransactionAsync(new Realm.Transaction() {
                                             @Override
                                             public void execute(Realm realm) {
                                                 user_response user = realm.where(user_response.class).findFirst();
                                                 //user.fullName = fn;
                                                 user.Pass = pass;
                                                 user.mobile = phone;
                                                 user.picId = imgidofprofile;
                                                 realm.copyToRealmOrUpdate(user);

                                             }
                                         });


                                     }else {
                                         Toast.makeText(ProfileActivity.this , R.string.errorupdate, Toast.LENGTH_LONG).show();
                                     }


                                } catch (Exception e) {
                                    Toast.makeText(ProfileActivity.this , e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });



                }else {
                    Toast.makeText(ProfileActivity.this ,e,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Compress compress = new Compress();
            String[] NameAndUUid;
            try {
                NameAndUUid = compress.compressImage(data.getData() , this);
              input_image.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
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
                            Toast.makeText(ProfileActivity.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ProfileActivity.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });
                imgidofprofile = NameAndUUid[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}

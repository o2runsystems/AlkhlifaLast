package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2runsystems.dev2.kh_fam.databinding.ActivityAddReportBinding;

import java.io.File;
import java.io.IOException;

import MangWorkFeature.PojoClasses.Famous;
import MangWorkFeature.PojoClasses.Reports;
import MangWorkFeature.Presenters.AddFamousePresnter;
import MangWorkFeature.Presenters.AddReportsPresenter;
import MangWorkFeature.Presenters.AddReportsReqiredView;
import Utiles.Compress;
import WebServices.Api.UploadServices;
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


public class AddReport extends BaseActivity implements AddReportsReqiredView {

    ActivityAddReportBinding reportBinding;
    String imgid = null;
    AddReportsPresenter presnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportBinding = DataBindingUtil.setContentView(this , R.layout.activity_add_report);
        reportBinding.bookfab.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 1);
        });
        presnter = new AddReportsPresenter(this);

        reportBinding.btnSignup.setOnClickListener(view -> {
            Reports report = new Reports();
            report.pic = imgid;
            report.text = reportBinding.txtbody.getText().toString();
            report.name = reportBinding.txtforname.getText().toString();
            presnter.AddReport(report);

        });
        reportBinding.btnBack.setOnClickListener(view -> {
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
                reportBinding.img.setImageBitmap(BitmapFactory.decodeFile(NameAndUUid[1]));
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
                            Toast.makeText(AddReport.this , "Image Uploaded  !" , Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(AddReport.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(AddReport.this , "Error To Upload Image" , Toast.LENGTH_LONG).show();
                    }
                });
                imgid = NameAndUUid[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void ShowTost(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CloseActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presnter.Ondestroy();
    }
}
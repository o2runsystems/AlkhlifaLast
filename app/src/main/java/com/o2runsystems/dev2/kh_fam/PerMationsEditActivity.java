package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.databinding.ActivityPerMationsEditBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import WebServices.Api.MemoService;
import WebServices.Models.UrlInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerMationsEditActivity extends BaseActivity {
     ActivityPerMationsEditBinding peractivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       peractivity = DataBindingUtil.setContentView(this, R.layout.activity_per_mations_edit );

        spinner();
       Intent en = getIntent();
        peractivity.nametxtforPer.setText("الاسـم كـاملا : "+en.getStringExtra("name"));
        peractivity.phonetxtForper.setText("رقـم الهـاتف : "+en.getStringExtra("phone"));
        peractivity.fkzper.setText("الفخذ الرئـيسي : "+en.getStringExtra("fk"));
        peractivity.genderforper.setText("الجنــس : "+en.getStringExtra("gender"));
        peractivity.numberofkid.setText("عدد الابناء : "+en.getStringExtra("numberofkid"));
        peractivity.job.setText(" المسمي الوظيفي : "+en.getStringExtra("job"));
        peractivity.compny.setText("جهة العمل : "+en.getStringExtra("compny"));
        peractivity.state.setText("الحالة: "+en.getStringExtra("state"));

        String url = UrlInfo.DownloadUrl+ en.getStringExtra("pic") +".Jpg";
        Picasso.with(this).load(url).placeholder(R.drawable.avatar).into(peractivity.picId);

        final int getid = en.getIntExtra("id",0);
        peractivity.btnsaveforper.setOnClickListener(view -> {

            NetWork(getid);

        });

        peractivity.btndelete.setOnClickListener(view -> {
          NetWorkDelete(getid);
        });

        switch (en.getIntExtra("exper",0)){
            case 0:
                peractivity.experForper.setText("الصـلاحـيه السـابقه : "+"غير مصرح له");
                break;
            case 1:
                peractivity.experForper.setText("الصـلاحـيه السـابقه : "+"مصرح له");
                break;
            case 2:
                peractivity.experForper.setText("الصـلاحـيه السـابقه : "+"المدير");
                break;
            case 3:
                peractivity.experForper.setText("الصـلاحـيه السـابقه : "+"المنسق");
                break;
            case 4 :
                peractivity.experForper.setText("الصـلاحـيه السـابقه : "+"مسـوق");
                break;
        }

    }

    void NetWork(int getid){
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInfo.AppUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MemoService ser = retrofit.create(MemoService.class);
        final Call<ResponseBody> reslt = ser.UpdatePerForUser(getid , Permid);
        reslt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerMationsEditActivity.this , "Updated" , Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(PerMationsEditActivity.this , "Error Happens" , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PerMationsEditActivity.this , "Error Happens" , Toast.LENGTH_LONG).show();
            }
        });
    }

    void NetWorkDelete(int getid){

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInfo.AppUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MemoService ser = retrofit.create(MemoService.class);
        final Call<ResponseBody> reslt = ser.Delete(getid);

        reslt.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerMationsEditActivity.this , "Deleted" , Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(PerMationsEditActivity.this , "Error Happens" , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PerMationsEditActivity.this , "Error Happens" , Toast.LENGTH_LONG).show();
            }
        });
    }

    int Permid;

    void spinner (){
        //1
        List<String> namesper = new ArrayList<>();
        namesper.add("غير مصرح له");
        namesper.add("مصرح له");
        namesper.add("المـدير");
        namesper.add("المنـسق");
        namesper.add("مســوق");
        namesper.add("علاقات عامة");


        try {
            ArrayAdapter<String> dataadpter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item , namesper);
            dataadpter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            peractivity.spenrforPerm.setAdapter(dataadpter1);

             peractivity.spenrforPerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                     Permid = i;
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> adapterView) {

                 }
             });
        }catch (Exception e){

        }

    }

}

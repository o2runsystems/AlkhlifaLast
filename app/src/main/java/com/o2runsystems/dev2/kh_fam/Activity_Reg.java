package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2runsystems.dev2.kh_fam.databinding.ActivityRegBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import WebServices.Api.MemoService;
import WebServices.Models.UrlInfo;
import WebServices.Models.Userses;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Activity_Reg extends BaseActivity {

    String mainbar , error , educationlevel , state;
    boolean gender =  false;
    Boolean valid ;
    ActivityRegBinding activityRegBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegBinding = DataBindingUtil.setContentView(this , R.layout.activity__reg );
        List<String> data = new ArrayList<String>();
        data.add("ذكــر");
        data.add("انثــي");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data);

        activityRegBinding.gender.setAdapter(dataAdapter);

        activityRegBinding.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("ذكــر")){
                    gender = true;

                }else {
                    gender = false;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> mainbardata = new ArrayList<String>();
        mainbardata.add("الســليمان");
        mainbardata.add("العبــد الله");
        mainbardata.add( "المحــمد");
        mainbardata.add( "المنيــع");

        ArrayAdapter<String> adaptermainbar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mainbardata);

        activityRegBinding.mainBar.setAdapter(adaptermainbar);
        activityRegBinding.mainBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainbar = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        List<String> levleofeddata = new ArrayList<String>();
        levleofeddata.add("ابـــتدائي");
        levleofeddata.add("متــوسط");
        levleofeddata.add("ثــانوي");
        levleofeddata.add("دبــلوم");
        levleofeddata.add("جــامعي");
        levleofeddata.add("ماجــستير");
        levleofeddata.add("دكــتواره");
        levleofeddata.add("لا يوجــــد");

        ArrayAdapter<String> levelofeduadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levleofeddata);
        activityRegBinding.levelOfEdcation.setAdapter(levelofeduadapter);

        activityRegBinding.levelOfEdcation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                educationlevel = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> statedata = new ArrayList<String>();
        statedata.add("حــي يرزق");
        statedata.add("مــتوفي");

        ArrayAdapter<String> stateadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statedata);
        activityRegBinding.state.setAdapter(stateadapter);
        activityRegBinding.state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        valdate();
        signUp();
    }


   void signUp(){
       activityRegBinding.btnSignup.setOnClickListener(new signclick());
   }

    private class signclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
          String  e = getResources().getString(R.string.error_field_required);
          if (Required()){
              if (valid){
                 // checking();
                  Gson gson =new GsonBuilder().create();
                  Retrofit retrofit = new Retrofit.Builder()
                          .baseUrl(UrlInfo.AppUrl)
                          .addConverterFactory(GsonConverterFactory.create(gson))
                          .build();
                  MemoService service = retrofit.create(MemoService.class);
                  final String user = activityRegBinding.inputUser.getText().toString();
                  Call<ResponseBody> check = service.check_user(user);
                  check.enqueue(new Callback<ResponseBody>() {
                      @Override
                      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                          try {
                              String s= response.body().string();
                              // do this
                              if (s.equals("false")){
                                  // do this && get insert function
                                  //Toast.makeText(Activity_Reg.this ,"ok" , Toast.LENGTH_LONG ).show();

                                  Gson gsoninsert = new GsonBuilder().create();
                                  Retrofit retrofit = new Retrofit.Builder()
                                          .baseUrl(UrlInfo.AppUrl).addConverterFactory(GsonConverterFactory.create(gsoninsert))
                                          .build();
                                  MemoService serviceinsert = retrofit.create(MemoService.class);
                                  final Userses user = new Userses();
                                  user.userName = activityRegBinding.inputUser.getText().toString();
                                  user.Pass = activityRegBinding.inputPassword2.getText().toString();
                                  user.civilNumber = activityRegBinding.civilNumber.getText().toString();
                                  user.fname = activityRegBinding.fname.getText().toString();
                                  user.ffName = activityRegBinding.ffName.getText().toString();
                                  user.fffName = activityRegBinding.fffName.getText().toString();
                                  user.ffffName = activityRegBinding.fffffName.getText().toString();
                                  user.gender = gender;
                                  user.mainBar = mainbar;
                                  user.levelOfEdcation = educationlevel;
                                  user.numberOfKids = activityRegBinding.numberOfKids.getText().toString();
                                  user.mobile = activityRegBinding.mobile.getText().toString();
                                  user.job = activityRegBinding.job.getText().toString();
                                  user.compony = activityRegBinding.compony.getText().toString();
                                  user.fatherId = activityRegBinding.fatherId.getText().toString();
                                  user.email = activityRegBinding.email.getText().toString();
                                  user.city = activityRegBinding.city.getText().toString();
                                  //user.picId = activityRegBinding.picId.getText().toString();
                              //    user.idCopy = activityRegBinding.idCopy.getText().toString();
                                  user.state = state;
                                  user.perid = 0;
                                  user.dateoFbearth = activityRegBinding.dateoFbearth.getText().toString();

                                  Call<Boolean> add = serviceinsert.adding(user);
                                  add.enqueue(new Callback<Boolean>() {
                                      @Override
                                      public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                          if (response.code() == 200) {
                                              if (response.body())
                                              Toast.makeText(Activity_Reg.this , R.string.add_seccuss, Toast.LENGTH_LONG).show();
                                              startActivity(new Intent(getApplicationContext() , Login_activity.class));
                                              finish();
                                          }else {
                                              Toast.makeText(getApplicationContext() , "لم يتم الحفظ تاكد من اعدادات الشبكة" , Toast.LENGTH_LONG).show();
                                          }


                                      }

                                      @Override
                                      public void onFailure(Call<Boolean> call, Throwable t) {
                                          Toast.makeText(Activity_Reg.this , t.getMessage(), Toast.LENGTH_LONG).show();
                                      }
                                  });
                                  activityRegBinding.inputUser.setError(null);




                              }else {
                                  activityRegBinding.inputUser.setError(getString(R.string.check_username));
                                  Toast.makeText(Activity_Reg.this , R.string.check_username ,Toast.LENGTH_LONG).show();
                              }

                          } catch (IOException e) {
                              Toast.makeText(Activity_Reg.this , e.getMessage() , Toast.LENGTH_LONG).show();
                          }
                      }

                      @Override
                      public void onFailure(Call<ResponseBody> call, Throwable t) {

                      }
                  });





              }else{
                  Toast.makeText(Activity_Reg.this ,error,Toast.LENGTH_LONG).show();
              }

          }else{
              Toast.makeText(Activity_Reg.this ,e,Toast.LENGTH_LONG).show();
          }

        }
    }

    Boolean  Required(){
    String u, p1,p2,n,p;
    u = activityRegBinding.inputUser.getText().toString();
    p1 = activityRegBinding.inputPassword1.getText().toString();
    p2 = activityRegBinding.inputPassword2.getText().toString();
    n = activityRegBinding.fname.getText().toString() + " " + activityRegBinding.fname.getText().toString() + " " + activityRegBinding.ffName.getText().toString() + " " + activityRegBinding.fffName.getText().toString() + " " + activityRegBinding.fffffName.getText().toString();
    p = activityRegBinding.mobile.getText().toString();

   if ((u.equals(null) || u.equals("")) || (p1.equals(null) || p1.equals("")) || (p2.equals(null) || p2.equals("")) || (n.equals(null) || n.equals("")) || (p.equals(null) || p.equals(""))  )
    {
        return false;
    }else {
      return true;
   }
}

    void valdate(){

        activityRegBinding.inputPassword2.setOnFocusChangeListener(new onfouucchangelistner());
    }


    private class onfouucchangelistner implements OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b){
                String t1 = activityRegBinding.inputPassword1.getText().toString();
                String t2 =  activityRegBinding.inputPassword2.getText().toString();
                if (!t1.equals(t2)){
                   // error_incorrect_password
                    activityRegBinding.inputPassword2.setError(error);
                    valid = false;
                Toast.makeText(Activity_Reg.this ,error,Toast.LENGTH_LONG).show();
            }else {
                    valid = true;
                    activityRegBinding.inputPassword2.setError(null);
                }

            }
        }
    }


}







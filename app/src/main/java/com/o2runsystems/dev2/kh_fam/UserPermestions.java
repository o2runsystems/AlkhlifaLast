package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Utiles.GsonUTCDateAdapter;

import java.util.Date;
import java.util.List;

import Adabters.ListviewUserAdabter;
import WebServices.Api.MemoService;
import WebServices.Models.UrlInfo;
import WebServices.Models.Userses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPermestions extends BaseActivity {
    ListView list;
    ListviewUserAdabter adabter;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_permestions);
         list = (ListView) findViewById(R.id.RecyclerViewForPerm);
        SearchView searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adabter.FilterMydata(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
           Network();
            return false;
        });


        Toolbar tl = (Toolbar) findViewById(R.id.pertool);
        setSupportActionBar(tl);
         title = "المســتخدمــين";
        getSupportActionBar().setTitle(title);

       Network();

    }

    void Network(){

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class , new GsonUTCDateAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInfo.AppUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MemoService ser = retrofit.create(MemoService.class);
        Call<List<Userses>> reslt = ser.GetAllUsers();
        reslt.enqueue(new Callback<List<Userses>>() {
            @Override
            public void onResponse(Call<List<Userses>> call, Response<List<Userses>> response) {
                final List<Userses> users = response.body();
                list.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent en = new Intent(UserPermestions.this , PerMationsEditActivity.class);
                    Userses oneuser = users.get(i);
                    en.putExtra("id", oneuser.id );
                    en.putExtra("name", oneuser.fname + " " + oneuser.ffName + " " + oneuser.fffName + " " + oneuser.ffffName );
                    en.putExtra("phone", oneuser.mobile);
                    en.putExtra("fk" , oneuser.mainBar);

                    if(oneuser.gender){
                        en.putExtra("gender" , "ذكـــر");
                    }else {
                        en.putExtra("gender" , "انثـــي");
                    }

                    en.putExtra("exper" , oneuser.perid);

                    en.putExtra("numberofkid" , oneuser.numberOfKids);
                    en.putExtra("job" , oneuser.job);
                    en.putExtra("compny" , oneuser.compony);
                    en.putExtra("state" , oneuser.state);
                    en.putExtra("pic" , oneuser.picId);
                    startActivity(en);
                });

                adabter = new ListviewUserAdabter(UserPermestions.this ,users);
                getSupportActionBar().setTitle("");
                list.setAdapter(adabter);
                String counttitle = title += " : " + users.size();
                getSupportActionBar().setTitle(counttitle);
            }

            @Override
            public void onFailure(Call<List<Userses>> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(UserPermestions.this ,t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmember , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addmember){
            Intent en = new Intent(this , Activity_Reg.class);
            startActivity(en);
            return true;
        }
        return false;
    }
}

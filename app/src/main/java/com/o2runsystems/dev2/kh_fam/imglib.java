package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Adabters.ImglibAdabter;
import WebServices.Api.ImgLibServices;
import WebServices.Models.ImgLibModel;
import WebServices.Models.UrlInfo;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class imglib extends BaseActivity {

    Realm realms;
    ImglibAdabter adbt;
    List<ImgLibModel> models = new ArrayList<>();
     RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imglib);
        realms = Realm.getDefaultInstance();
        rec = (RecyclerView)findViewById(R.id.imglibrec);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary , R.color.colorPrimaryDark);

        Toolbar tobar = (Toolbar) findViewById(R.id.toolsbr);
        setSupportActionBar(tobar);
        getSupportActionBar().setTitle("مكتــبه الصــور");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 3);
        rec.setLayoutManager(gridLayoutManager);
        // get from database realm

        try {

            //region get data in  database  Realm and query form database postgress
            RealmResults<ImgLibModel>  sdf = realms.where(ImgLibModel.class).findAll();
        if (sdf.size() > 0){
            models.addAll(sdf);
        }

        adbt = new ImglibAdabter(imglib.this,models , rec);
        rec.setAdapter(adbt);

        Number ids = realms.where(ImgLibModel.class).max("id");
        int id;
        if (ids == null){
            id = 0;
        }else {
            id = ids.intValue();
        }


        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlInfo.AppUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ImgLibServices services = retrofit.create(ImgLibServices.class);
        Call<List<ImgLibModel>> reslt = services.GetAllImgLib(id);
        reslt.enqueue(new Callback<List<ImgLibModel>>() {
            @Override
            public void onResponse(Call<List<ImgLibModel>> call, final Response<List<ImgLibModel>> response) {
                int i = models.size() ;
        if (response.body().size() > 0){
               models.addAll(response.body());
            adbt.notifyItemRangeChanged(i , response.body().size());
            rec.smoothScrollToPosition(models.size());
            realms.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(response.body());
                }
            });
        }

            }

            @Override
            public void onFailure(Call<List<ImgLibModel>> call, Throwable t) {

            }
        });
            //endregion

        }catch (Exception e){
            e.printStackTrace();
        }


        //region refreshLayout
        refreshLayout.setOnRefreshListener(() -> {
            try {

            Number ids = realms.where(ImgLibModel.class).max("id");
            int id;
            if (ids == null){
                id = 0;
            }else {
                id = ids.intValue();
            }


            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlInfo.AppUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ImgLibServices services = retrofit.create(ImgLibServices.class);
            Call<List<ImgLibModel>> reslt = services.GetAllImgLib(id);
            reslt.enqueue(new Callback<List<ImgLibModel>>() {
                @Override
                public void onResponse(Call<List<ImgLibModel>> call, final Response<List<ImgLibModel>> response) {
                    int i = models.size() ;
                    if (response.body().size() > 0){
                        models.addAll(response.body());
                        adbt.notifyItemRangeChanged(i , response.body().size());
                        rec.smoothScrollToPosition(models.size());
                        realms.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(response.body()));
                    }

                }

                @Override
                public void onFailure(Call<List<ImgLibModel>> call, Throwable t) {

                }
            });

            }catch (Exception e){
                e.printStackTrace();
            }



            refreshLayout.setRefreshing(false);
        });
        //endregion

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addimglibmenu , menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     if (resultCode == RESULT_OK && requestCode == 1){

         final ImgLibModel model = new ImgLibModel();
         model.id = data.getIntExtra("id",1);
         model.imgurl = data.getStringExtra("imgurl");
         model.name = data.getStringExtra("name");
         model.sumrize = data.getStringExtra("sumrizing");
         int i = models.size() ;
         models.add(model);
         adbt.notifyItemRangeChanged(i ,1);
         rec.smoothScrollToPosition(models.size());
         realms.executeTransactionAsync(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {
                 realm.copyToRealmOrUpdate(model);
             }
         });
     }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addlibMenuBar){
            Intent en = new Intent(this , ImgLibOprations.class);
            en.putExtra("IsUpdate" , false);
            startActivityForResult(en,1);
            return true;
        }
        return false;
    }
}

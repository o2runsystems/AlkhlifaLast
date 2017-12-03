package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import PublishFeature.ApiServices.PublishServices;
import Utiles.RecyclerItemOnDoubleClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Adabters.CardViewAdapter;
import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.UrlInfo;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.realm.Realm;
import WebServices.Models.user_response;
public class PorActivity extends BaseActivity {
    List<Publishs> models1 = new ArrayList<>();
    Realm realms;
    RecyclerView rec;
    CardViewAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("الشعر");


        final SwipeRefreshLayout swipeRefresh  = (SwipeRefreshLayout) findViewById(R.id.swipe_container_his);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary , R.color.colorPrimaryDark);


        realms = Realm.getDefaultInstance();
        // RecyclerView
        rec = (RecyclerView) findViewById(R.id.rec_his);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rec.setLayoutManager(layoutManager);
        try {
            // read
            RealmResults<Publishs> sdf = realms.where(Publishs.class).equalTo("Type", 4).findAll();
            if (sdf.size() > 0){
                models1.addAll(sdf);
            }
            adapter = new CardViewAdapter(models1 , this , rec , view -> {} , view -> {} , view -> {} , view -> {});
            rec.setAdapter(adapter);


            Number ids =  realms.where(Publishs.class).equalTo("Type",4).max("id");
            int id;
            if (ids == null)
            {
                id = 0;
            }else {
                id =  ids.intValue();
            }

            Gson gson = new GsonBuilder().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlInfo.AppUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
            PublishServices service = retrofit.create(PublishServices.class);
            Call<List<Publishs>> get = service.getpublishpor(id);
            get.enqueue(new Callback<List<Publishs>>() {
                @Override
                public void onResponse(Call<List<Publishs>> call, final Response<List<Publishs>> response) {
                    int i = models1.size();
                    if (response.body().size() > 0) {
                        // get last update !
                        models1.addAll(response.body());
                        adapter.notifyItemRangeChanged(i , response.body().size());
                        rec.smoothScrollToPosition(models1.size() );
                        // insert data in Realm
                        realms.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(response.body());
                            }


                        });
                    }
                }
                @Override

                public void onFailure(Call<List<Publishs>> call, Throwable t) {
                    //   Toast.makeText(getActivity() , t.getMessage() ,Toast.LENGTH_LONG).show();
                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }
        //region Click Card
        rec.addOnItemTouchListener(new RecyclerItemOnDoubleClickListener(this, new RecyclerItemOnDoubleClickListener.OnItemDoubleClickListener() {
            @Override
            public void onItemDoubleClick(View view, int position) {
                try {
                    List<Publishs> m = models1;
                    Intent intent =new Intent(PorActivity.this , ShowActivity.class);
                    intent.putExtra("user",m.get(position).UserName);
                    SimpleDateFormat d= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    d.setTimeZone(TimeZone.getTimeZone("UTC+3"));
                    String dts = d.format(m.get(position).DateTime);
                    intent.putExtra("time" , dts);
                    intent.putExtra("titel",m.get(position).Title);
                    intent.putExtra("body" , m.get(position).Body);
                    intent.putExtra("avatar", m.get(position).Avatar);
                    intent.putExtra("image", m.get(position).Images);
                    startActivity(intent);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }));
        //endregion

        //region swipeRefresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    Number ids =  realms.where(Publishs.class).equalTo("Type",4).max("id");
                    int id;
                    if (ids == null)
                    {
                        id = 0;
                    }else {
                        id =  ids.intValue();
                    }

                    Gson gson = new GsonBuilder().create();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlInfo.AppUrl).addConverterFactory(GsonConverterFactory.create(gson)).build();
                    PublishServices service = retrofit.create(PublishServices.class);
                    Call<List<Publishs>> get = service.getpublishpor(id);
                    get.enqueue(new Callback<List<Publishs>>() {
                        @Override
                        public void onResponse(Call<List<Publishs>> call, final Response<List<Publishs>> response) {
                            int i = models1.size();
                            if (response.body().size() > 0) {
                                // get last update !
                                models1.addAll(response.body());
                                adapter.notifyItemRangeChanged(i , response.body().size());
                                rec.smoothScrollToPosition(models1.size() );
                                // insert data in Realm
                                realms.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(response.body());
                                    }


                                });
                            }
                        }
                        @Override

                        public void onFailure(Call<List<Publishs>> call, Throwable t) {
                            //   Toast.makeText(getActivity() , t.getMessage() ,Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                swipeRefresh.setRefreshing(false);
            }
        });
        //endregion
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK &&  requestCode == 1){
            final Publishs model =new Publishs();
            model.id = data.getIntExtra("id" , 1);
            model.Title = data.getStringExtra("Title");
            model.Body = data.getStringExtra("Body");
            model.DateTime = (Date) data.getSerializableExtra("DateTime");
            model.Avatar = data.getStringExtra("Avatar");
            model.UserName = user ;
            model.Type = 4;
            model.Images  = data.getStringExtra("Images");
            int i = models1.size();
            models1.add(model);
            adapter.notifyItemRangeChanged(i , 1);
            rec.smoothScrollToPosition(models1.size());
            realms.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(model);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addimglibmenu , menu);
        return true;
    }

    void  getintent(){
        image = realms.where(user_response.class).findFirst().picId;
        user = realms.where(user_response.class).findFirst().userName;

    }

    String image ,user;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addlibMenuBar){
            Intent intent =new Intent(PorActivity.this , PostPublisher.class);
            intent.putExtra("avatar", image);
            intent.putExtra("user",user);
            intent.putExtra("type",4);
            startActivityForResult(intent,1);
            return true;
        }
        return false;
    }
}

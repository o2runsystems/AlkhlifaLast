package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Adabters.BookLibAdabter;
import WebServices.Api.BookLibServices;
import WebServices.Models.BookLibModel;
import WebServices.Models.UrlInfo;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookLib extends BaseActivity {
    Realm realms;
    BookLibAdabter adbter;
    List<BookLibModel> models = new ArrayList<>();
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lib);
        realms = Realm.getDefaultInstance();

        Toolbar tool = (Toolbar) findViewById(R.id.bookmainActivitylibtoolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("مكـتبه الكتــب");
        mRecyclerView = (RecyclerView) findViewById(R.id.booklibrecyclerview);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);


        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layout);


        try {

            //region get data in  database  Realm and query form database postgress
            RealmResults<BookLibModel> sdf = realms.where(BookLibModel.class).findAll();
            if (sdf.size() > 0) {
                models.addAll(sdf);
            }
            adbter = new BookLibAdabter(models, BookLib.this);
            mRecyclerView.setAdapter(adbter);

            Number ids = realms.where(BookLibModel.class).max("id");
            int id;
            if (ids == null) {
                id = 0;
            } else {
                id = ids.intValue();
            }


            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlInfo.AppUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            BookLibServices service = retrofit.create(BookLibServices.class);
            Call<List<BookLibModel>> reslts = service.GetAllBook(id);
            reslts.enqueue(new Callback<List<WebServices.Models.BookLibModel>>() {
                @Override
                public void onResponse(Call<List<WebServices.Models.BookLibModel>> call, final Response<List<WebServices.Models.BookLibModel>> response) {
                    int i = models.size();
                    if (response.body().size() > 0) {
                        models.addAll(response.body());
                        adbter.notifyItemRangeChanged(i, response.body().size());
                        mRecyclerView.smoothScrollToPosition(models.size());

                        realms.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(response.body());
                            }
                        });
                    }


                }

                @Override
                public void onFailure(Call<List<WebServices.Models.BookLibModel>> call, Throwable t) {
                    Toast.makeText(BookLib.this, "Error Crashed !", Toast.LENGTH_LONG).show();
                }
            });
            //endregion

        } catch (Exception e) {
            e.printStackTrace();
        }


        //region Refesh code
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {


                    Number ids = realms.where(BookLibModel.class).max("id");
                    int id;
                    if (ids == null) {
                        id = 0;
                    } else {
                        id = ids.intValue();
                    }


                    Gson gson = new GsonBuilder()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(UrlInfo.AppUrl)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    BookLibServices service = retrofit.create(BookLibServices.class);
                    Call<List<BookLibModel>> reslts = service.GetAllBook(id);
                    reslts.enqueue(new Callback<List<WebServices.Models.BookLibModel>>() {
                        @Override
                        public void onResponse(Call<List<WebServices.Models.BookLibModel>> call, final Response<List<WebServices.Models.BookLibModel>> response) {
                            int i = models.size();
                            if (response.body().size() > 0) {
                                models.addAll(response.body());
                                adbter.notifyItemRangeChanged(i, response.body().size());
                                mRecyclerView.smoothScrollToPosition(models.size());

                                realms.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(response.body());
                                    }
                                });
                            }


                        }

                        @Override
                        public void onFailure(Call<List<WebServices.Models.BookLibModel>> call, Throwable t) {
                            Toast.makeText(BookLib.this, "Error Crashed !", Toast.LENGTH_LONG).show();
                        }
                    });
                    refreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        //endregion

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {

            final BookLibModel model = new BookLibModel();
            model.id = data.getIntExtra("id", 1);
            model.author = data.getStringExtra("author");
            model.bookDescrib = data.getStringExtra("bookDescrib");
            model.downloadurl = data.getStringExtra("downloadurl");
            model.imgId = data.getStringExtra("imgId");
            model.name = data.getStringExtra("name");
            int i = models.size();
            models.add(model);
            adbter.notifyItemRangeChanged(i, 1);
            mRecyclerView.smoothScrollToPosition(models.size());
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
        getMenuInflater().inflate(R.menu.addimglibmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addlibMenuBar) {
            Intent en = new Intent(this, addbooklib.class);
            en.putExtra("IsUpdate", false);
            startActivityForResult(en, 1);
            return true;
        }
        return false;
    }
}

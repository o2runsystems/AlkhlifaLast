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
import android.widget.ImageView;
import android.widget.Toast;

import PublishFeature.Presenters.HistoryShowPresnter;
import PublishFeature.Presenters.HistoryShowViewReqires;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Adabters.CardViewAdapter;
import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.user_response;
import io.realm.Realm;
import io.realm.RealmResults;

public class HistroyActivity extends BaseActivity implements HistoryShowViewReqires {

    List<Publishs> models1 = new ArrayList<>();

    Realm realms;

    RecyclerView rec;
    ImageView  deletebtn , sharebtn;
    List<String> tages;
    CardViewAdapter adapter ;
    Integer per ;
    SwipeRefreshLayout swipeRefresh;

    String image ,user;

    HistoryShowPresnter presnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        Inint();

        FillOnline();

        swipeRefresh.setOnRefreshListener(() -> {
            try {

                Number ids =  realms.where(Publishs.class).equalTo("Type",3).max("id");
                int id;
                if (ids == null)
                {
                    id = 0;
                }else {
                    id =  ids.intValue();
                }

                presnter.GetHistoryList(id);

            }catch (Exception e){
                e.printStackTrace();
            }

            swipeRefresh.setRefreshing(false);
        });



    }

    void WholeCardClick(int position){
        try {
            List<Publishs> m = models1;
            Intent intent =new Intent(HistroyActivity.this , ShowActivity.class);
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

    void Inint(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تاريخ العائلة");

        swipeRefresh  = (SwipeRefreshLayout) findViewById(R.id.swipe_container_his);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary , R.color.colorPrimaryDark);


        realms = Realm.getDefaultInstance();

        per  = realms.where(user_response.class).findFirst().perid;

        rec = (RecyclerView) findViewById(R.id.rec_his);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rec.setLayoutManager(layoutManager);
        presnter = new HistoryShowPresnter(this);
    }

   void FillOnline(){
       try {


           adapter = new CardViewAdapter(models1, this, rec, view -> {
               WholeCardClick(rec.getChildLayoutPosition(view));

           }, view -> {


           } , view -> {

               deletebtn = (ImageView) view;
               if(per.equals(5) || per.equals(2)){

                   tages = (List<String>) deletebtn.getTag();
                   int id = Integer.parseInt(tages.get(0));
                   presnter.DeletePost(id);

               }else {
                   Toast.makeText(this, "لا يمكنك حذف الخبر ليست لديك الصلاحيه", Toast.LENGTH_SHORT).show();
                   deletebtn.setImageDrawable(null);
               }

           } , view -> {});
           rec.setAdapter(adapter);


           Number ids =  realms.where(Publishs.class).equalTo("Type",3).max("id");
           int id;
           if (ids == null)
           {
               id = 0;
           }else {
               id =  ids.intValue();
           }

          presnter.GetHistoryList(id);


       }catch (Exception e){
           e.printStackTrace();
       }
   }

    void  getintent(){
        image = realms.where(user_response.class).findFirst().picId;
        user = realms.where(user_response.class).findFirst().userName;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode ==1){

            final Publishs model =new Publishs();
            model.id = data.getIntExtra("id" , 1);
            model.Title = data.getStringExtra("Title");
            model.Body = data.getStringExtra("Body");
            model.DateTime = (Date) data.getSerializableExtra("DateTime");
            model.Avatar = data.getStringExtra("Avatar");
            model.UserName = user ;
            model.Type = 3;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addlibMenuBar){
            Intent intent =new Intent(HistroyActivity.this , PostPublisher.class);
            intent.putExtra("avatar", image);
            intent.putExtra("user",user);
            intent.putExtra("type",3);
            startActivityForResult(intent,1);
            return true;
        }
        return false;
    }

    @Override
    public void FillRecyclerView(List<Publishs> publishsList) {
        models1.clear();
        int i = models1.size();
        if (publishsList.size() > 0) {

            models1.addAll(publishsList);
            adapter.notifyItemRangeChanged(i , publishsList.size());
            rec.smoothScrollToPosition(models1.size() );

            realms.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(publishsList));
        }

        }

    @Override
    public void ShowToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeleteCard() {
        int pos = Integer.parseInt(tages.get(1));;
        models1.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

}

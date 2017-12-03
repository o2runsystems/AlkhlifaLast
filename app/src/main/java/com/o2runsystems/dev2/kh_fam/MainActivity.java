package com.o2runsystems.dev2.kh_fam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.ChatFolder.ChatServices;

import Utiles.TabAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;

import Fragments.EventFramgment;
import Fragments.MarketFragment;
import Fragments.NewsFragment;
import WebServices.Models.UrlInfo;
import WebServices.Models.user_response;
import io.realm.Realm;
import io.realm.RealmResults;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int per;
    Realm realms;

    public static HubConnection conction;

    public static HubProxy proxy;

    boolean isConct = false;

    boolean servicesConcting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateFileIfNotExist();

        DrawerAndToolBarInit();

        SignalrWorkingCocntions();

    }

    void CreateFileIfNotExist() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        startService(new Intent(this, ChatServices.class));

        realms = Realm.getDefaultInstance();
        per = realms.where(user_response.class).findFirst().perid;
    }

    void DrawerAndToolBarInit() {

        //region DrawoerAndToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //toggle.setHomeAsUpIndicator(R.drawable.men);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TextView fnametxt = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navfname);
        ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.logo);
        String image = realms.where(user_response.class).findFirst().picId;
        String url2 = UrlInfo.DownloadUrl + image + ".Jpg";
        Picasso.with(this).load(url2).placeholder(R.drawable.avatar).into(imageView);
        user_response data = realms.where(user_response.class).findFirst();
        String fname = data.fname + " " + data.ffName + " " + data.fffName + " " + data.ffffName;
        fnametxt.setText(fname);


        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.AddFragment(new NewsFragment(), getString(R.string.news));
        adapter.AddFragment(new EventFramgment(), getString(R.string.events));
        adapter.AddFragment(new MarketFragment(), getString(R.string.market));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        //endregion

        //region Tab Layout
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        final TextView tab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab1.setText("الاخــــبار");
        

        tabLayout.getTabAt(0).setCustomView(tab1);


        final TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab1, null);
        tab2.setText("المنــاسبات");
        tabLayout.getTabAt(1).setCustomView(tab2);


        final TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab2, null);
        tab3.setText("الســوق");

        tabLayout.getTabAt(2).setCustomView(tab3);


        //endregion

        //region Post Publisher Logic
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            int tab = tabLayout.getSelectedTabPosition();

            switch (tab) {
                case 0: {
                    if (CalaculationDate()) {
                        Intent intent = new Intent(MainActivity.this, PostPublisher.class);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "عفوا العمر المسموح به فوق ال15 سنه", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }
                case 1: {
                    if (CalaculationDate()) {
                        Intent intent = new Intent(MainActivity.this, PostPublisher.class);
                        intent.putExtra("type", 2);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "عفوا العمر المسموح به فوق ال15 سنه", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 2: {
                    if (CalaculationDate()) {
                        Intent intent = new Intent(MainActivity.this, MarketActivityPuplisher.class);
                        startActivity(intent);
                    }
                    else{
                            Toast.makeText(this, "عفوا العمر المسموح به فوق ال15 سنه", Toast.LENGTH_SHORT).show();
                        }
                    break;
                }
            }


        });
        //endregion
    }


    boolean CalaculationDate(){

        long thiseayer = 1439;
       String userdate = realms.where(user_response.class).findFirst().dateoFbearth;
        long yearofperson = Long.parseLong(userdate.split("/")[2]);

        long lastdate = thiseayer - yearofperson;

        if(lastdate > 15){
            return true;
        }

        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings :{
                startActivity( new Intent(MainActivity.this ,ProfileActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.nav_camera) {

             if (per == 0){
                 Toast.makeText(this, R.string.authorized,Toast.LENGTH_LONG).show();
             }
             else
             {
                 startActivity(new Intent(this, imglib.class));
             }

            return true;
        }
        else if(id == R.id.nav_gallery) {

         startActivity(new Intent(this, BookLib.class));

            return true;

        }else if(id == R.id.chat){
            if (per == 0){
                Toast.makeText(this, R.string.authorized,Toast.LENGTH_LONG).show();
            }else {
            startActivity(new Intent(MainActivity.this , chatScrren.class));
            }
            return true;
        }
        else if(id== R.id.his){


            startActivity(new Intent(MainActivity.this , HistroyActivity.class));

        }

        else if(id == R.id.vote){

            if (per == 2|| per == 3){
            startActivity(new Intent(this , UserPermestions.class));
            }else {
                Toast.makeText(this, R.string.authorized,Toast.LENGTH_LONG).show();
            }
            return true;

        }

        else if(id == R.id.signout){

            realms.beginTransaction();
                    RealmResults<user_response> row= realms.where(user_response.class).findAll();
                     row.deleteFirstFromRealm();
            realms.commitTransaction();

             startActivity(new Intent(MainActivity.this ,Login_activity.class));

            return true;

        }

        else if(id == R.id.about){
            startActivity(new Intent(MainActivity.this , about.class));
        }
        else if(id == R.id.famousmenu){
            startActivity(new Intent(MainActivity.this , FamousShow.class));
        }else if(id == R.id.box){
            startActivity(new Intent(MainActivity.this , BoxShow.class));
        }else if(id == R.id.report){
            startActivity(new Intent(MainActivity.this , ReportShow.class));
        }else if(id == R.id.sug2){
            startActivity(new Intent(MainActivity.this , ContactSho.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(isConct == false){
            conction.start();
            isConct = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isConct == false){
            conction.start();
            isConct = true;
        }

    }


    void  SignalrWorkingCocntions(){
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        String Url = UrlInfo.ChatHubUrls;
        conction = new HubConnection(Url);
        proxy = conction.createHubProxy("card");
    }
}


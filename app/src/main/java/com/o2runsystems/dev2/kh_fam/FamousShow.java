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

import java.util.ArrayList;
import java.util.List;

import Adabters.FamousAdabter;
import MangWorkFeature.PojoClasses.Famous;
import MangWorkFeature.Presenters.FamousShowViewReqired;
import MangWorkFeature.Presenters.FamouseShowPresnter;

public class FamousShow extends BaseActivity implements FamousShowViewReqired {
    RecyclerView recyclerView;
    List<Famous> fmaslist = new ArrayList<>();
    FamousAdabter adabter;
    FamouseShowPresnter presnter;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_show);
        ToolBarInint();
        RecylcerViewInint();
        presnter = new FamouseShowPresnter(this);
        presnter.GetListFamouse();
    }

    void ToolBarInint(){
        Toolbar tool = (Toolbar) findViewById(R.id.famustoolbarshow);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("اشهر رجال العائلة");
    }

     void RecylcerViewInint(){
        recyclerView = (RecyclerView) findViewById(R.id.famouShowRecycler);

         final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
         refreshLayout.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary , R.color.colorPrimaryDark);
         refreshLayout.setOnRefreshListener(() -> {
             presnter.GetListFamouse();
             refreshLayout.setRefreshing(false);
         });

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout);
         adabter = new FamousAdabter(this , recyclerView , fmaslist , view -> {

           pos =  Integer.parseInt(view.getTag().toString());
             int id = fmaslist.get(pos).id;
         presnter.DeleteFamous(id);

         });
         recyclerView.setAdapter(adabter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addimglibmenu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addlibMenuBar){
            Intent en = new Intent(this, AddFamous.class);
            startActivity(en);
            return true;
        }
        return false;
    }

    @Override
    public void ShowToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void FillRecylerView(List<Famous> data) {
        fmaslist.clear();
        fmaslist.addAll(data);
        adabter.notifyDataSetChanged();
    }

    @Override
    public void DeleteitemFromRecyclerView() {
        fmaslist.remove(pos);
        adabter.notifyItemRemoved(pos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     presnter.OnDestory();
    }
}

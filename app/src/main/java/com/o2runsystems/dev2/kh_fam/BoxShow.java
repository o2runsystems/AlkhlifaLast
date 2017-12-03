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

import java.util.ArrayList;
import java.util.List;

import Adabters.BoxAdabter;
import MangWorkFeature.PojoClasses.Boxes;
import MangWorkFeature.Presenters.BoxesShowPresnter;
import MangWorkFeature.Presenters.BoxesShowViewReqired;

public class BoxShow extends BaseActivity implements BoxesShowViewReqired {

    RecyclerView recyclerView;
    List<Boxes> fmaslist = new ArrayList<>();
    BoxAdabter adabter;
    BoxesShowPresnter presnter;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_show);
        presnter = new BoxesShowPresnter(this);
        ToolBarInint();
        RecylcerViewInint();
        presnter.GetListOfBoxes();
    }

    void ToolBarInint(){
        Toolbar tool = (Toolbar) findViewById(R.id.boxtoolbarshow);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("الصناديق الخيرية");
    }

    void RecylcerViewInint(){
        recyclerView = (RecyclerView) findViewById(R.id.famouShowRecycler);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent , R.color.colorPrimary , R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(() -> {
            presnter.GetListOfBoxes();
            refreshLayout.setRefreshing(false);
        });

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setReverseLayout(true);
        layout.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout);
        adabter = new BoxAdabter(this , recyclerView , fmaslist , view -> {

            pos =  Integer.parseInt(view.getTag().toString());
            int id = fmaslist.get(pos).id;
            presnter.DeleteBox(id);

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
            Intent en = new Intent(this, AddBox.class);
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
    public void FillRecylerView(List<Boxes> boxes) {
        fmaslist.clear();
        fmaslist.addAll(boxes);
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
        presnter.Ondestory();
    }
}

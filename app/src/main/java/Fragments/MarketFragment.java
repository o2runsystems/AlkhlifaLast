package Fragments;

import Adabters.MarketViewAdapter;
import PublishFeature.PojoClasses.MarketModel;
import PublishFeature.PojoClasses.Publishs;
import PublishFeature.Presenters.MarketShowPresnter;
import PublishFeature.Presenters.MarketShowViewReqired;
import WebServices.Models.user_response;
import io.realm.Realm;
import io.realm.RealmResults;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.MainActivity;
import com.o2runsystems.dev2.kh_fam.R;
import com.o2runsystems.dev2.kh_fam.show_market;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class MarketFragment extends Fragment implements MarketShowViewReqired {

    List<MarketModel> models =new ArrayList<>();
    Realm realms;
    RecyclerView rec;
    MarketViewAdapter adapter;
     SwipeRefreshLayout swipe;
    MarketShowPresnter presnter;
    ImageView img , deletebtn;
    Integer per ;
    List<String> tages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signalrWork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.market_layout, container, false);

        RecyclerViewInint(view);
        presnter = new MarketShowPresnter(this);
        int id = FillOfflineRetrunId();
        FillOnline(id);

        return view;

    }

    //region signalrFunc
    void signalrWork(){

        MainActivity.proxy.on("GetBroadcastCardSendForMarket" , new SubscriptionHandler1<MarketModel>(){
            @Override
            public void run(final MarketModel cardModel) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        models.add(cardModel);
                        int pos = models.size()-1;
                        adapter.notifyItemInserted(pos);
                        rec.smoothScrollToPosition(pos);
                        realms.beginTransaction();
                        realms.copyToRealmOrUpdate(cardModel);
                        realms.commitTransaction();

                    }
                });

            }
        }, MarketModel.class);



    }
    //endregion

    //region OndestroyCallBack(Close SignalrContions)
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //endregion

    public void RecyclerViewInint(View view){

        rec = (RecyclerView) view.findViewById(R.id.market_rec);
        final LinearLayoutManager layoutManager =new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rec.setLayoutManager(layoutManager);
         swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipe.setColorSchemeResources(R.color.colorAccent ,R.color.colorPrimary , R.color.colorPrimaryDark);


        //region Refresh Card
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Number ids = realms.where(MarketModel.class).max("id");
                int id;
                if (ids == null) {
                    id = 0;
                } else {
                    id = ids.intValue();
                }
                FillOnline(id);
                swipe.setRefreshing(false);
            }
        });
        //endregion

    }

    public int FillOfflineRetrunId() {

        realms = Realm.getDefaultInstance();

        per  = realms.where(user_response.class).findFirst().perid;

            //region get data in  database  Realm and query form database postgress
            RealmResults<MarketModel> sdf = realms.where(MarketModel.class).findAll();
            if (sdf.size() > 0) {
         //       models.addAll(sdf);
            }

            adapter = new MarketViewAdapter(models, getContext(), rec , view -> {
                WholeCardClick(rec.getChildLayoutPosition(view));

            } , view -> {

                img = (ImageView) view;
                int id = Integer.parseInt(img.getTag().toString());
                presnter.ConfermPost(id);

            } , view -> {

                deletebtn = (ImageView) view;
                if(per.equals(5) || per.equals(2)){

                    tages = (List<String>) deletebtn.getTag();
                    int id = Integer.parseInt(tages.get(0));
                    presnter.DeletePost(id);

                }else {
                    Toast.makeText(getActivity(), "لا يمكنك حذف الخبر ليست لديك الصلاحيه", Toast.LENGTH_SHORT).show();
                    deletebtn.setImageDrawable(null);
                }

            });
            rec.setAdapter(adapter);

            Number ids =  realms.where(MarketModel.class).max("id");
            int id;
            if (ids == null)
            {
                id = 0;
            }else {
                id =  ids.intValue();
            }

            return 0;
    }

    public void FillOnline(int id){
      presnter.GetMarketList(id);

    }

    void WholeCardClick(int position){
        try {
            List<MarketModel> m = models;
            Intent intent =new Intent(getContext() , show_market.class);
            intent.putExtra("img", m.get(position).img);
            intent.putExtra("name",m.get(position).name);
            intent.putExtra("price",m.get(position).price);
            intent.putExtra("about",m.get(position).about);
            intent.putExtra("type" , m.get(position).type);
            intent.putExtra("cat", m.get(position).cat);

            SimpleDateFormat d= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            d.setTimeZone(TimeZone.getTimeZone("UTC+3"));
            String dts = d.format(m.get(position).time);
            intent.putExtra("time" , dts);
            intent.putExtra("phone",m.get(position).phone);
            startActivity(intent);

        }catch (Exception e){e.printStackTrace();}

    }


    @Override
    public void FillRecyclerView(List<MarketModel> lists) {

        models.removeAll(models);


        if(per.equals(5)|| per.equals(2)){

            models.addAll(lists);
            }

            else {

                for (MarketModel pub : lists){
                    if(pub.isConfermet){
                        models.add(pub);
                    }
                }
            }

        adapter.notifyItemChanged(0,models.size());
        rec.smoothScrollToPosition(models.size());

    }

    @Override
    public void ShowToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void WorkWithRealm(boolean state, int id) {
        img.setImageDrawable(null);
        Toast.makeText(getContext(), "تم الاعتماد بنجاح", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeleteCard() {
        int pos = Integer.parseInt(tages.get(1));;
        models.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presnter.Ondestroy();
    }
}

package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.MainActivity;
import com.o2runsystems.dev2.kh_fam.R;

import com.o2runsystems.dev2.kh_fam.ShowActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Adabters.CardViewAdapter;
import PublishFeature.PojoClasses.Publishs;
import PublishFeature.Presenters.EventShowPresnter;
import PublishFeature.Presenters.EventShowViewReqired;
import WebServices.Models.user_response;
import io.realm.Realm;
import io.realm.RealmResults;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;


public class EventFramgment extends Fragment implements EventShowViewReqired {

    List<Publishs> models1 = new ArrayList<>();
    Realm realms;
    RecyclerView rec;
    CardViewAdapter adapter ;
     SwipeRefreshLayout swipe;
    EventShowPresnter presnter;
    ImageView  deletebtn, sharebtn;
    TextView img;
    List<String> tages;
    Integer per;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signalrWork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all_layout, container, false);

            RecyclerViewInint(view);

        presnter = new EventShowPresnter(this);

            int id = FillOfflineAndRetrunId();

            FillOnline(id);

        return view;


    }


    public int FillOfflineAndRetrunId() {

        realms = Realm.getDefaultInstance();
        per  = realms.where(user_response.class).findFirst().perid;

            RealmResults<Publishs> sdf = realms.where(Publishs.class).equalTo("Type",2).findAll();
            if (sdf.size() > 0) {
             //   models1.addAll(sdf);
            }

            adapter = new CardViewAdapter(models1, getContext(), rec , view -> {
                WHolCardClick(rec.getChildLayoutPosition(view));
            } , view -> {
                img = (TextView) view;
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

            } , view -> {

                sharebtn = (ImageView)view;

                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(sharebtn.getTag().toString())
                        .getIntent();
                if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(shareIntent);
                }

            } , view -> {
                ImageView  img2 = (ImageView) view;
                int id = Integer.parseInt(img2.getTag().toString());
            Publishs model =    models1.get(id);
                SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                Calendar cal = Calendar.getInstance();
                Date d = new Date(2017,9,3);
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime",  yyyyMMdd.format(cal.getTime()));
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", yyyyMMdd.format(d));
                intent.putExtra("title", model.Title);
                startActivity(intent);

            });
            rec.setAdapter(adapter);

            Number ids =  realms.where(Publishs.class).equalTo("Type",2).max("id");
            int id;
            if (ids == null)
            {
                id = 0;
            }else {
                id =  ids.intValue();
            }
            return 0;
    }

    public void RecyclerViewInint(View view){
        rec = (RecyclerView) view.findViewById(R.id.rec);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rec.setLayoutManager(layoutManager);
         swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //region Refresh Card
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FillOnline(0);
                swipe.setRefreshing(false);

            }
        });
        //endregion
    }

    void WHolCardClick(int position){
        try {                    List<Publishs> m = models1;
            Intent intent =new Intent(getContext() , ShowActivity.class);
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

    public void FillOnline(int id){
    presnter.GetHistoryList(id);
    }

    //region signalrFunc
    void signalrWork(){


        MainActivity.proxy.on("GetBroadcastCardSendForEvents" , new SubscriptionHandler1<Publishs>(){
            @Override
            public void run(final Publishs cardModel) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        models1.add(cardModel);
                        int pos = models1.size()-1;
                        adapter.notifyItemInserted(pos);
                        rec.smoothScrollToPosition(pos);
                        realms.beginTransaction();
                        realms.copyToRealmOrUpdate(cardModel);
                        realms.commitTransaction();

                    }
                });

            }
        }, Publishs.class);



    }
    //endregion

    @Override
    public void onDestroy() {
        super.onDestroy();
        realms.close();

    }

    @Override
    public void FillRecyclerView(List<Publishs> list) {

        models1.clear();

            if(per.equals(5)|| per.equals(2)){

                models1.addAll(list);

            }
            else {

                for (Publishs pub : list){
                    if(pub.isConfermet){
                        models1.add(pub);
                    }
                }
            }
        adapter.notifyDataSetChanged();
        rec.smoothScrollToPosition(models1.size());


    }

    @Override
    public void ShowToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void WorkWithRealm(boolean state, int id) {
        img.setVisibility(View.GONE);
        Toast.makeText(getContext(), "تم الاعتماد بنجاح", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DeleteCard() {
        int pos = Integer.parseInt(tages.get(1));;
        models1.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presnter.Ondestroy();
    }
}

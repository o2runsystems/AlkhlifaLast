package Adabters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.o2runsystems.dev2.kh_fam.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import PublishFeature.PojoClasses.MarketModel;
import WebServices.Models.UrlInfo;

/**
 * Created by dev2 on 10/12/2016.
 */

public class MarketViewAdapter extends RecyclerView.Adapter<MarketViewAdapter.MyViewHolder> {
List<MarketModel> models;
    Context context ;
    RecyclerView rec ;
    View.OnClickListener cardlistin;
    View.OnClickListener confirmbtnlisten;
    View.OnClickListener deletbtn;



    public MarketViewAdapter(List<MarketModel> m , Context c , RecyclerView r , View.OnClickListener _cardlistin , View.OnClickListener _confirmbtnlisten , View.OnClickListener _deletebtn){
        models = m;
        context = c;
        rec = r;
        cardlistin = _cardlistin;
        confirmbtnlisten = _confirmbtnlisten;
        deletbtn = _deletebtn;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_card, parent , false);

        MyViewHolder holder = new MyViewHolder(v);
        holder.cardView.setOnClickListener(cardlistin);
        holder.confirmbtn.setOnClickListener(confirmbtnlisten);
        holder.deletebtn.setOnClickListener(deletbtn);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MarketModel model = models.get(position);
        holder.market_name.setText(model.name);
        holder.market_about.setText(model.about);
        holder.market_price.setText(model.price );
        holder.market_cat.setText(model.cat);
        holder.market_type.setText(model.type);
        SimpleDateFormat d= new SimpleDateFormat("dd/MM");
        d.setTimeZone(TimeZone.getTimeZone("UTC+3"));
        String time = d.format(model.time);
        holder.market_time.setText(time);
        String url = UrlInfo.DownloadUrl+ model.img +".Jpg";
        Picasso.with(context).load(url).placeholder(R.drawable.market).fit().into(holder.img);

        List<String> tages = new ArrayList<>();
        tages.add(model.id +"");
        tages.add(position + "");
        holder.deletebtn.setTag(tages);

        if(model.isConfermet){
            holder.confirmbtn.setImageDrawable(null);
        }else{
            holder.confirmbtn.setTag(model.id);
        }

    }



    @Override
    public int getItemCount() {
        if (models != null){
        return models.size();}
        else {
            return  0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView ;
        ImageView img , confirmbtn , deletebtn;
        TextView market_name , market_about , market_price , market_cat , market_type , market_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)  itemView.findViewById(R.id.market_card);
            img = (ImageView)  itemView.findViewById(R.id.market_img);
            market_name = (TextView)  itemView.findViewById(R.id.market_name);
            market_about = (TextView)  itemView.findViewById(R.id.market_about);
            market_price = (TextView)  itemView.findViewById(R.id.market_price);
            market_cat = (TextView)  itemView.findViewById(R.id.market_cat);
            market_type = (TextView)  itemView.findViewById(R.id.market_type);
            market_time = (TextView)  itemView.findViewById(R.id.market_time);
            confirmbtn = (ImageView)  itemView.findViewById(R.id.imageView3);
            deletebtn = (ImageView)  itemView.findViewById(R.id.imageView6);
        }
    }
}



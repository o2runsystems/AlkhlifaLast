package Adabters;

import PublishFeature.PojoClasses.Publishs;
import WebServices.Models.UrlInfo;

import android.content.Context;
import android.graphics.Typeface;
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

/**
 * Created by dev2 on 10/5/2016.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {
Context context;
List<Publishs> models;
    RecyclerView rcd;
    View.OnClickListener cardlist , confirmlisten , deletebtn , sharebtn , addcalnderbtn;


    public CardViewAdapter(List<Publishs> models ,Context context , RecyclerView rc , View.OnClickListener _cardlist  , View.OnClickListener _confirmlisten ,  View.OnClickListener _deletebtn ,  View.OnClickListener _sharebtn ){
        this.models = models;
        this.context = context;
        rcd = rc;
        cardlist = _cardlist;
        confirmlisten = _confirmlisten;
        deletebtn = _deletebtn;
        sharebtn = _sharebtn;

    }

    public CardViewAdapter(List<Publishs> models ,Context context , RecyclerView rc , View.OnClickListener _cardlist  , View.OnClickListener _confirmlisten ,  View.OnClickListener _deletebtn ,  View.OnClickListener _sharebtn , View.OnClickListener _addcalnderbtn){
        this.models = models;
        this.context = context;
        rcd = rc;
        cardlist = _cardlist;
        confirmlisten = _confirmlisten;
        deletebtn = _deletebtn;
        sharebtn = _sharebtn;
        addcalnderbtn = _addcalnderbtn;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent ,false);
        Typeface type = Typeface.createFromAsset(context.getAssets() , "fonts/DroidKufiRegular.ttf");

        MyViewHolder holder =new MyViewHolder(v);
        holder.time.setTypeface(type);
        holder.confirmbtn.setTypeface(type);
        holder.carder.setOnClickListener(cardlist);
        holder.confirmbtn.setOnClickListener(confirmlisten);
        holder.deletebtn.setOnClickListener(deletebtn);
        holder.sharebtn.setOnClickListener(sharebtn);

        if(addcalnderbtn !=null){
            holder.addcalnderbtn.setOnClickListener(addcalnderbtn);

        }


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Publishs model = models.get(position);
         holder.username.setText(model.UserName);
        holder.titel.setText(model.Title);
        holder.body.setText(model.Body);

        SimpleDateFormat d= new SimpleDateFormat("dd/MM");
        d.setTimeZone(TimeZone.getTimeZone("UTC+3"));
        String dts = d.format(model.DateTime);
        holder.time.setText(dts);
        String url = UrlInfo.DownloadUrl+ model.Avatar +".Jpg";
        String url2 = UrlInfo.DownloadUrl+ model.Images +".Jpg";
        Picasso.with(context).load(url).placeholder(R.drawable.avatar).into(holder.avater);
        Picasso.with(context).load(url2).into(holder.imagebody);

        List<String> tages = new ArrayList<>();
        tages.add(model.id +"");
        tages.add(position + "");
        holder.deletebtn.setTag(tages);
        String data = model.UserName + "\n" + "--------------------------------"  + "\n" + "بتــاريخ  : " + d.format(model.DateTime) + "\n" + "--------------------------------" + "\n"+ model.Title+ "\n" + "--------------------------------" + "\n" + model.Body;
        holder.sharebtn.setTag(data);

        if(model.isConfermet){
           holder.confirmbtn.setVisibility(View.GONE);
        }else{
            holder.confirmbtn.setTag(model.id);
        }

        if(addcalnderbtn !=null){
            holder.addcalnderbtn.setTag(position);
        }

        if(model.Type ==3){
            holder.sharebtn.setVisibility(View.GONE);
            holder.addcalnderbtn.setVisibility(View.GONE);
            holder.confirmbtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(models != null) {
       return models.size();}
        else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView carder;
        TextView username,time,titel,body;
        ImageView avater,imagebody , deletebtn , sharebtn , addcalnderbtn;

        TextView confirmbtn;


        public MyViewHolder(View itemView) {
            super(itemView);
            carder = (CardView) itemView.findViewById(R.id.carder);
            username = (TextView) itemView.findViewById(R.id.txt_name);
            time = (TextView) itemView.findViewById(R.id.txt_date);
            titel = (TextView) itemView.findViewById(R.id.txtfortitle);
            body = (TextView) itemView.findViewById(R.id.txtforbody);
            avater = (ImageView) itemView.findViewById(R.id.logo);
            imagebody = (ImageView) itemView.findViewById(R.id.imgforpic);
            confirmbtn = (TextView) itemView.findViewById(R.id.confirm);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteimag);
            sharebtn = (ImageView) itemView.findViewById(R.id.imageView);
            addcalnderbtn = (ImageView) itemView.findViewById(R.id.imageView8);


        }
    }


}

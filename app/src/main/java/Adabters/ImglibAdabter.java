package Adabters;

import WebServices.Models.ImgLibModel;
import WebServices.Models.UrlInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.o2runsystems.dev2.kh_fam.R;
import com.o2runsystems.dev2.kh_fam.imglibdetials;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dev on 9/29/2016.
 */
public class ImglibAdabter extends RecyclerView.Adapter<BklibHolder> {
    Context mContext;
    List<ImgLibModel> models;
    RecyclerView mrc;

    public ImglibAdabter(Context con , List<ImgLibModel> lists , RecyclerView rc) {
        mContext = con;
        models = lists;
        mrc = rc;
    }


    @Override
    public BklibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.imglibrow, parent , false);
        BklibHolder bk = new BklibHolder(row , mContext);
        //must be fixed to increese performance
        bk.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = mrc.getChildLayoutPosition(view);

                Intent en = new Intent(mContext , imglibdetials.class);
                ImgLibModel model = models.get(pos);
                en.putExtra("name" , model.name);
                en.putExtra("imgurl" , model.imgurl);
                en.putExtra("info" , model.sumrize);
                mContext.startActivity(en);
            }
        });
        return bk;
    }

    @Override
    public void onBindViewHolder(BklibHolder holder, final int position) {

        ImgLibModel model = models.get(position);
        String str = UrlInfo.DownloadUrl+ model.imgurl +".Jpg";

     Picasso.with(mContext).load(str).placeholder(R.drawable.plholder).into(holder.Img);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


}


 class BklibHolder extends RecyclerView.ViewHolder  {

     ImageView Img;
     Context cons;

    public BklibHolder(View itemView , Context con) {
        super(itemView);
        cons = con;
        Img = (ImageView)itemView.findViewById(R.id.imglibimg);
    }

 }
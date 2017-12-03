package Adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.o2runsystems.dev2.kh_fam.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import WebServices.Models.UrlInfo;
import WebServices.Models.Userses;

/**
 * Created by dev on 10/16/2016.
 */

public class ListviewUserAdabter extends BaseAdapter {

    List<Userses> musers ;
    Context cons;

    public ListviewUserAdabter(Context con , List<Userses> userss){
         cons = con;
        musers = userss;
    }

    @Override
    public int getCount() {
        return musers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PerRowHolderforlistview holder;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.peruserrow , viewGroup , false);
            holder = new PerRowHolderforlistview();
            holder.PerRowTxt = (TextView) view.findViewById(R.id.permtxtrow);
            holder.img = (ImageView) view.findViewById(R.id.perimgrow);
            view.setTag(holder);
        }else {
            holder = (PerRowHolderforlistview) view.getTag();
        }
        Userses user = musers.get(i);
        holder.PerRowTxt.setText(user.fname + " " + user.ffName + " " + user.fffName + " " + user.ffffName);
        String str = UrlInfo.DownloadUrl+ user.picId +".Jpg";
        Picasso.with(cons).load(str).placeholder(
                R.drawable.avatar).into(holder.img);
        return view;
    }
    List<Userses> newuserlist = new ArrayList<>();
    public void FilterMydata(String data){
        newuserlist.clear();
        for(Userses us : musers){
            if(us.fname.contains(data) | us.ffName.contains(data) | us.fffName.contains(data)){
                newuserlist.add(us);
            }
        }
        musers.clear();
        musers.addAll(newuserlist);
        notifyDataSetChanged();
    }
}

class PerRowHolderforlistview{
    TextView PerRowTxt;
    ImageView img;


}

package Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.o2runsystems.dev2.kh_fam.R;

import java.util.ArrayList;
import java.util.List;

import WebServices.Models.Userses;

import static io.realm.log.RealmLog.clear;

/**
 * Created by dev on 10/14/2016.
 */

public class PermRowAdabter extends RecyclerView.Adapter<PerRowHolder>{
    List<Userses> usernames;
    Context mcontex;
    public PermRowAdabter(List<Userses> us , Context mcon){
        usernames = us;
        mcontex = mcon;
    }
    @Override
    public PerRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PerRowHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.peruserrow, parent , false));
    }

    @Override
    public void onBindViewHolder(PerRowHolder holder, int position) {
        Userses us = usernames.get(position);
        holder.PerRowTxt.setText(us.fname + " " + us.ffName + " " + us.fffName + " " + us.ffffName + " " );
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }




}

class PerRowHolder extends RecyclerView.ViewHolder{
    TextView PerRowTxt;

    public PerRowHolder(View itemView) {
        super(itemView);
        PerRowTxt = (TextView) itemView.findViewById(R.id.permtxtrow);
    }
}

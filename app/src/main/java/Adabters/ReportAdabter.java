package Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.o2runsystems.dev2.kh_fam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import MangWorkFeature.PojoClasses.Reports;
import WebServices.Models.UrlInfo;

/**
 * Created by dev on 7/18/2017.
 */

public class ReportAdabter extends RecyclerView.Adapter<ReportAdabter.ReportHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Reports> models;
    View.OnClickListener _deletclick;

    public ReportAdabter(Context _context , RecyclerView rec , List<Reports> model , View.OnClickListener deletclick ){
        context = _context;
        recyclerView = rec;
        models = model;
        _deletclick = deletclick;
    }

    @Override
    public ReportAdabter.ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.famouscard,parent ,false);
        ReportAdabter.ReportHolder holder =new ReportAdabter.ReportHolder(v);
        holder.deletebtn.setOnClickListener(_deletclick);

        return holder;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(ReportAdabter.ReportHolder holder, int position) {
        Reports model = models.get(position);
        holder.txt.setText(model.text);
        holder.txtforname.setText(model.name);
        holder.deletebtn.setTag(position);
        String url = UrlInfo.DownloadUrl+ model.pic +".Jpg";
        Picasso.with(context).load(url).placeholder(R.drawable.avatar).into(holder.img);
    }




    public class ReportHolder extends RecyclerView.ViewHolder {
        TextView txt , txtforname;
        ImageView img , deletebtn;

        public ReportHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txtforbody);
            txtforname = (TextView) itemView.findViewById(R.id.txtfortitle);
            img = (ImageView) itemView.findViewById(R.id.imgforpic);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteimag);
        }


    }

}

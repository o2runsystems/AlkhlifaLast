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

import MangWorkFeature.PojoClasses.Famous;
import WebServices.Models.UrlInfo;


public class FamousAdabter extends RecyclerView.Adapter<FamousAdabter.FamousHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Famous> models;
    View.OnClickListener _deletclick;

    public FamousAdabter(Context _context , RecyclerView rec , List<Famous> model ,View.OnClickListener deletclick ){
        context = _context;
        recyclerView = rec;
        models = model;
        _deletclick = deletclick;
    }

    @Override
    public FamousHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.famouscard,parent ,false);
        FamousHolder holder =new FamousHolder(v);
        holder.deletebtn.setOnClickListener(_deletclick);

        return holder;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(FamousHolder holder, int position) {
       Famous model = models.get(position);
        holder.txt.setText(model.text);
        holder.txtforname.setText(model.name);
        holder.deletebtn.setTag(position);
        String url = UrlInfo.DownloadUrl+ model.pic +".Jpg";
        Picasso.with(context).load(url).placeholder(R.drawable.avatar).into(holder.img);
    }




    public class FamousHolder extends RecyclerView.ViewHolder {
        TextView txt , txtforname;
        ImageView img , deletebtn;

        public FamousHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txtforbody);
            txtforname = (TextView) itemView.findViewById(R.id.txtfortitle);
            img = (ImageView) itemView.findViewById(R.id.imgforpic);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteimag);
        }


    }

}

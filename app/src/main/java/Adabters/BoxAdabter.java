package Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.o2runsystems.dev2.kh_fam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import MangWorkFeature.PojoClasses.Boxes;
import MangWorkFeature.PojoClasses.Famous;
import WebServices.Models.UrlInfo;


public class BoxAdabter  extends RecyclerView.Adapter<BoxAdabter.BoxHolder>  {

    Context _context;
    RecyclerView _recyclerView;
    List<Boxes> _models;
    View.OnClickListener _deletclick;

    public BoxAdabter(Context context , RecyclerView recyclerView , List<Boxes> models ,  View.OnClickListener deletclick){
        _context = context;
        _recyclerView = recyclerView;
        _models = models;
        _deletclick = deletclick;


    }

    @Override
    public BoxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.boxcard,parent ,false);
        BoxHolder holder =new BoxHolder(v);
        holder.deletebtn.setOnClickListener(_deletclick);
        return holder;
    }

    @Override
    public void onBindViewHolder(BoxHolder holder, int position) {
        Boxes box = _models.get(position);
        holder.txtfortitle.setText(box.title);
        holder.txtforbody.setText(box.body);
        holder.txtforiban.setText(box.iban);
        holder.deletebtn.setTag(position);

        String url = UrlInfo.DownloadUrl+ box.pic +".Jpg";
        Picasso.with(_context).load(url).placeholder(R.drawable.avatar).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return _models.size();
    }

    public class BoxHolder extends RecyclerView.ViewHolder {
        TextView txtfortitle , txtforbody , txtforiban;
        ImageView img , deletebtn;

        public BoxHolder(View itemView) {
            super(itemView);
            txtfortitle = (TextView) itemView.findViewById(R.id.txtfortitle);
            txtforbody = (TextView) itemView.findViewById(R.id.txtforbody);
            txtforiban = (TextView) itemView.findViewById(R.id.txtforiban);
            img = (ImageView) itemView.findViewById(R.id.imgforpic);
            deletebtn = (ImageView) itemView.findViewById(R.id.deleteimag);
        }
    }
}

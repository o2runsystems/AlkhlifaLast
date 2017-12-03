package Adabters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2runsystems.dev2.kh_fam.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import WebServices.Models.BookLibModel;
import WebServices.Models.UrlInfo;

/**
 * Created by dev on 10/5/2016.
 */
public class BookLibAdabter extends RecyclerView.Adapter<BookLibHolder>{
    List<BookLibModel> models ;
    Context mContext;
    public BookLibAdabter(List<BookLibModel> pmodels , Context con){
        models = pmodels;
        mContext = con;
    }

    @Override
    public BookLibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklibrow, parent , false);
        BookLibHolder holder = new BookLibHolder(view);
        holder.txturlDownload.setMovementMethod(LinkMovementMethod.getInstance());
        return holder;
    }

    @Override
    public void onBindViewHolder(BookLibHolder holder, int position) {
        BookLibModel row = models.get(position);
        holder.txtNameOfBook.setText("اسم الكتاب : "+row.name);
        holder.txtArthor.setText("مؤلفه : "+row.author);
       String link = row.downloadurl;
        String shortcut = "رابط التحميل";
         String href = "<a href=\""+link+"\">"+shortcut+"</a>";
        
        holder.txturlDownload.setText(Html.fromHtml(href));
        String url = UrlInfo.DownloadUrl+ row.imgId +".Jpg";
        Picasso.with(mContext).load(url).placeholder(R.drawable.plholder).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}


class BookLibHolder extends ViewHolder{

    ImageView img ;
    TextView txtNameOfBook;
    TextView txtArthor;
    TextView txturlDownload;


    public BookLibHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.booklibBookImage);
        txtNameOfBook = (TextView) itemView.findViewById(R.id.booklibBookName);
        txtArthor = (TextView) itemView.findViewById(R.id.booklibBookAuthor);
        txturlDownload = (TextView) itemView.findViewById(R.id.booklibBookUrlDonwloads);

    }

}
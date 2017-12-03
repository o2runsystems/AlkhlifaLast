package Adabters;

import WebServices.Models.TextChatMessage;
import WebServices.Models.ViewsNames;
import WebServices.Models.UrlInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.o2runsystems.dev2.kh_fam.R;
import com.o2runsystems.dev2.kh_fam.imagezoom;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dev on 10/2/2016.
 */
public class ChatAdabter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TextChatMessage>  messages ;
    ViewsNames viewsNames = new ViewsNames();
    Context mContext;
    RecyclerView mRecycler;

    public ChatAdabter(Context cpn,List<TextChatMessage> msgs , RecyclerView rc){
        messages = msgs;
        mContext = cpn;
        mRecycler = rc;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType == viewsNames.RghitTextRow){
           View Row2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rghitrow , parent, false);
           return new RightTextCahtHolder(Row2);
       }else if(viewType == viewsNames.LeftTextRow){
           View Row = LayoutInflater.from(parent.getContext()).inflate(R.layout.leftrow , parent, false);

           return new LeftTextChatHolder(Row);
       }else if(viewType == viewsNames.RghitImgRow){
           View Row3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rightmediarow , parent, false);
           RightImgChat imgchat = new RightImgChat(Row3);
           imgchat.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = mRecycler.getChildLayoutPosition(view);
                   Intent en = new Intent(mContext , imagezoom.class);
                   String url = UrlInfo.DownloadUrl + messages.get(pos).MessageBody + ".Jpg";
                   en.putExtra("imgurl", url);
                   mContext.startActivity(en);
               }
           });
           return imgchat;
       }else if(viewType == viewsNames.LeftImgRow){
           View Row4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.leftmediarow , parent, false);
           LeftImgChat leftImgChat = new LeftImgChat(Row4);
           leftImgChat.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = mRecycler.getChildLayoutPosition(view);
                   Intent en = new Intent(mContext , imagezoom.class);
                   String url = UrlInfo.DownloadUrl + messages.get(pos).MessageBody + ".Jpg";
                   en.putExtra("imgurl",url);
                   mContext.startActivity(en);
               }
           });
           return leftImgChat;
       }
       else{
           return null;
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
          TextChatMessage message = messages.get(position);
        if(message.TypeAndDir == viewsNames.RghitTextRow){
            RightTextCahtHolder myholder = (RightTextCahtHolder) holder;
            myholder.txtcchatbodyRight.setText(message.MessageBody);
            myholder.txtchatTxttimeRight.setText(message.MessageTime);
            myholder.txtchatTxtxFromRight.setText(message.From);
        }else if(message.TypeAndDir == viewsNames.LeftTextRow){
            LeftTextChatHolder myholder = (LeftTextChatHolder) holder;
            myholder.txtcchatbodyLeft.setText(message.MessageBody);
            myholder.txtchatTxttimeLeft.setText(message.MessageTime);
            myholder.txtchatTxtxFromLeft.setText(message.From);
        }else if (message.TypeAndDir == viewsNames.RghitImgRow){
            RightImgChat chatholder = (RightImgChat) holder;
            chatholder.imgchatTimeRight.setText(message.MessageTime);
            String url = UrlInfo.DownloadUrl + message.MessageBody + ".Jpg";
            Picasso.with(mContext).load(url).into(chatholder.imgchatPhotoRight);
        }else if (message.TypeAndDir == viewsNames.LeftImgRow){
            LeftImgChat leftchatholder = (LeftImgChat) holder;
            leftchatholder.imgchatTimeLeft.setText(message.MessageTime);
            String url = UrlInfo.DownloadUrl + message.MessageBody + ".Jpg";
            Picasso.with(mContext).load(url).into(leftchatholder.imgchatPhotoLeft);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).TypeAndDir;
    }
}


class LeftTextChatHolder extends RecyclerView.ViewHolder {
    TextView txtcchatbodyLeft;
    TextView txtchatTxttimeLeft;
    TextView txtchatTxtxFromLeft;
    public LeftTextChatHolder(View itemView) {
        super(itemView);
        txtcchatbodyLeft = (TextView) itemView.findViewById(R.id.chatrowtxtbodyleft);
        txtchatTxttimeLeft = (TextView) itemView.findViewById(R.id.chatrowtxttimeleft);
        txtchatTxtxFromLeft = (TextView) itemView.findViewById(R.id.chatRowTextFromtxtLeft);
    }
}


class RightTextCahtHolder extends RecyclerView.ViewHolder {

    TextView txtchatTxtxFromRight;
    TextView txtcchatbodyRight;
    TextView txtchatTxttimeRight;
    public RightTextCahtHolder(View itemView ) {
        super(itemView);
        txtcchatbodyRight = (TextView) itemView.findViewById(R.id.chattxtbodyrghit);
        txtchatTxttimeRight = (TextView) itemView.findViewById(R.id.chattxttimerghit);
        txtchatTxtxFromRight = (TextView) itemView.findViewById(R.id.chatRowTextFromtxtRight);
    }
}


class RightImgChat extends RecyclerView.ViewHolder {
    ImageView imgchatPhotoRight;
    TextView imgchatTimeRight;

    public RightImgChat(View itemView) {
        super(itemView);
        imgchatPhotoRight = (ImageView) itemView.findViewById(R.id.chatimgPhotoRight);
        imgchatTimeRight = (TextView) itemView.findViewById(R.id.chatImgetimerghit);
    }
}


class LeftImgChat extends RecyclerView.ViewHolder {
    ImageView imgchatPhotoLeft;
    TextView imgchatTimeLeft;

    public LeftImgChat(View itemView) {
        super(itemView);
        imgchatPhotoLeft = (ImageView) itemView.findViewById(R.id.chatimgPhotoLeft);
        imgchatTimeLeft = (TextView) itemView.findViewById(R.id.chatImgtimeleft);
    }

}
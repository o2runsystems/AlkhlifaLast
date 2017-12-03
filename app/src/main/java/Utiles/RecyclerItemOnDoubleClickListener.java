package Utiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dev2 on 12/5/2016.
 */

public class RecyclerItemOnDoubleClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemDoubleClickListener DoubleClickListener;
    public interface OnItemDoubleClickListener{
        public void onItemDoubleClick(View view , int position);
    }
    GestureDetector gestureDetector;
   public RecyclerItemOnDoubleClickListener(Context context ,  OnItemDoubleClickListener Listener){
       DoubleClickListener = Listener;

       gestureDetector = new GestureDetector(context , new GestureDetector.SimpleOnGestureListener(){
           @Override
           public boolean onDoubleTap(MotionEvent e) {
               return true;
           }
       });
   }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childview = rv.findChildViewUnder(e.getX(),e.getY());
        if (DoubleClickListener != null && childview != null && gestureDetector.onTouchEvent(e)){
            DoubleClickListener.onItemDoubleClick(childview ,rv.getChildAdapterPosition(childview) );
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

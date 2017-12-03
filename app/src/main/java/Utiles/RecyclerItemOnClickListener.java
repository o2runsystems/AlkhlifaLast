package Utiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dev2 on 10/6/2016.
 */
public class RecyclerItemOnClickListener implements RecyclerView.OnItemTouchListener {


    private OnItemClickListener ClickListener;
    public interface OnItemClickListener{
        public void onItemClick(View view , int position);
    }
GestureDetector gestureDetector;
    public  RecyclerItemOnClickListener(Context context ,OnItemClickListener listener ){
        ClickListener = listener;

        gestureDetector = new GestureDetector(context , new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
           View childview = rv.findChildViewUnder(e.getX(),e.getY());
        if (ClickListener != null && childview != null && gestureDetector.onTouchEvent(e)){
            ClickListener.onItemClick(childview ,rv.getChildAdapterPosition(childview) );
        }

        return false;
    }


// not use
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }
// not use
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

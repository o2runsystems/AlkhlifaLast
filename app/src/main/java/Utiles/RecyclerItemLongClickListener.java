package Utiles;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dev2 on 10/6/2016.
 */
public class RecyclerItemLongClickListener implements RecyclerView.OnItemTouchListener {


    private  OnItemLongClickListener longClickListener;
    public interface OnItemLongClickListener{
        public void OnItemLongClick(View view , int position);
    }
    @Nullable
    View childview;
    int childviewpostion;
GestureDetector gestureDetector ;
    public RecyclerItemLongClickListener(Context context ,OnItemLongClickListener listener ){
        longClickListener = listener;
        gestureDetector = new GestureDetector(context ,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
           longClickListener.OnItemLongClick(childview ,childviewpostion);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childview = rv .findChildViewUnder(e.getX() ,e.getY());

        if (longClickListener !=null && childview != null && gestureDetector.onTouchEvent(e)){
                longClickListener.OnItemLongClick(childview ,rv .getChildAdapterPosition(childview) );
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

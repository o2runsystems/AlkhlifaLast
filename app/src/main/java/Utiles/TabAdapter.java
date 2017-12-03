package Utiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev2 on 10/4/2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter  {

     List<Fragment> fragments ;
     List<String>  fragmentsname;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
       fragmentsname = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int postion){
        return fragmentsname.get(postion);
    }

    public void AddFragment(Fragment f, String s  ){
           fragments.add(f);
           fragmentsname.add(s);
    }
}

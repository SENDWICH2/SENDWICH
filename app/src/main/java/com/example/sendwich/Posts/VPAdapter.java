package com.example.sendwich.Posts;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sendwich.Posts.Advertise.AdvertiseFragment;
import com.example.sendwich.Posts.Combine.CombineFragment;
import com.example.sendwich.Posts.Select.SelectFragment;

import java.util.ArrayList;

/*
 뷰페이저 안에 들어가는 탭
 */

public class VPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public VPAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new SelectFragment());
        items.add(new CombineFragment());
        items.add(new AdvertiseFragment());
        
        itext.add("선택게시판");
        itext.add("통합게시판");
        itext.add("HOT위치");
    }

    public CharSequence getPageTitle(int position){
        return itext.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

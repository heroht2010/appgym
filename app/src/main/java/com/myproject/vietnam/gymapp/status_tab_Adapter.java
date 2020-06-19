package com.myproject.vietnam.gymapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class status_tab_Adapter extends FragmentStatePagerAdapter {
    private String listtab[]={"Chờ xác nhận","Đang giao","Đã giao","Đã hủy"};
    private fragment_status_wait fragment_status_wait;
    private fragment_status_delivering fragment_status_delivering;
    private fragment_status_delivered fragment_status_delivered;
    private fragment_status_cancel fragment_status_cancel;
    public status_tab_Adapter(@NonNull FragmentManager fm) {
        super(fm);
        fragment_status_wait=new fragment_status_wait();
        fragment_status_delivering=new fragment_status_delivering();
        fragment_status_delivered=new fragment_status_delivered();
        fragment_status_cancel=new fragment_status_cancel();

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return fragment_status_wait;
        }
        else{
            if (position==1){
                return fragment_status_delivering;
            }
            else{
                if (position==2){
                    return fragment_status_delivered;
                }
                else{
                    if (position==3){
                        return fragment_status_cancel;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return listtab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)  {
        return listtab[position];
    }
}

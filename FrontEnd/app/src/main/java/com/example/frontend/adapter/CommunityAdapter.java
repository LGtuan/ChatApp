package com.example.frontend.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.frontend.fragment.CommunityFragment;
import com.example.frontend.fragment.FriendFragment;
import com.example.frontend.fragment.UserFragment;

public class CommunityAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"Friend","User"};

    public CommunityAdapter(@NonNull CommunityFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 1 ) return new UserFragment();
        return new FriendFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}

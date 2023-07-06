package com.example.frontend.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frontend.R;
import com.example.frontend.adapter.CommunityAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class CommunityFragment extends Fragment {
    private String[] titles = new String[]{"Friend","User"};
    ViewPager2 viewPager2;
    CommunityAdapter communityAdapter;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        viewPager2 = rootView.findViewById(R.id.viewpager_fragment_community);
        tabLayout = rootView.findViewById(R.id.tablayout_fragment_community);
        communityAdapter = new CommunityAdapter(this);

        viewPager2.setAdapter(communityAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {
            tab.setText(titles[position]);
        })).attach();

        return rootView;
    }
}
package com.jerry.fanju_manager.main.home.fanju.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.databinding.FragmentHome1Binding;

import cn.leancloud.LCUser;

public class Home1Fragment extends Fragment {

    private FragmentHome1Binding binding;
    private ViewPager fanjuPager;
    private TabLayout fanjuTabs;
    private FloatingActionButton fanjuFab;

    public Home1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().getActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHome1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FanjuSectionsAdapter adapter = new FanjuSectionsAdapter(getContext(), getChildFragmentManager());
        fanjuPager = binding.fanjuViewPager;
        fanjuPager.setAdapter(adapter);
        fanjuTabs = binding.fanjuTabs;
        fanjuTabs.setupWithViewPager(fanjuPager);
        fanjuFab = binding.fab;
    }
}
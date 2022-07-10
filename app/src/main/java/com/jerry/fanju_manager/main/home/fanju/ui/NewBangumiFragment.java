package com.jerry.fanju_manager.main.home.fanju.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.databinding.FragmentHome1Binding;

public class NewBangumiFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private BangumiPageViewModel viewModel;
    private FragmentHome1Binding binding;

    public NewBangumiFragment() {
        // Required empty public constructor
    }

    public static NewBangumiFragment newInstance(int index) {
        Bundle args = new Bundle();
        NewBangumiFragment fragment = new NewBangumiFragment();
        args.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BangumiPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        viewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHome1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
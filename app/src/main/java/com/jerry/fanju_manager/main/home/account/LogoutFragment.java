package com.jerry.fanju_manager.main.home.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.main.login.LoginActivity;
import com.jerry.fanju_manager.main.viewmodel.LoginViewModel;

import cn.leancloud.LCUser;

public class LogoutFragment extends Fragment {
    private LoginViewModel loginViewModel;

    public LogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logout, container, false);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.logout_title)//设置对话框的标题
                .setMessage(R.string.logout_content)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavHostFragment.findNavController(LogoutFragment.this).navigate(R.id.action_logoutFragment_to_nav_home);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginViewModel.logOut();
                        LCUser.logOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

        return v;
    }
}
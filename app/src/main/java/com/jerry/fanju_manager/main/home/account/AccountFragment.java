package com.jerry.fanju_manager.main.home.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jerry.fanju_manager.R;

import cn.leancloud.LCObject;
import cn.leancloud.LCUser;
import cn.leancloud.types.LCNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AccountFragment extends Fragment {

    private EditText emailText;
    private EditText usernameText;
    private EditText phoneText;
    private Button resetPass;
    private Button editBtn;
    private Button saveBtn;
    private TextView infoText;
    private LCUser currentUser;

    private final int countTimer = 30000;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = LCUser.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        emailText = v.findViewById(R.id.accountSet_email);
        usernameText = v.findViewById(R.id.accountSet_username);
        phoneText = v.findViewById(R.id.accountSet_phone);
        resetPass = v.findViewById(R.id.accountSet_resetPass);
        editBtn = v.findViewById(R.id.accountSet_edit);
        saveBtn = v.findViewById(R.id.accountSet_save);
        infoText = v.findViewById(R.id.accountSet_info);

        loadData();
        initFunc();
        return v;
    }

    private void loadData() {
        emailText.setText(currentUser.getEmail());
        usernameText.setText(currentUser.getUsername());
        phoneText.setText(currentUser.getMobilePhoneNumber());
    }

    private void initFunc() {
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText.setEnabled(true);
                phoneText.setEnabled(true);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("session token", currentUser.getSessionToken());
                LCUser.becomeWithSessionTokenInBackground(currentUser.getSessionToken())
                        .subscribe(new Observer<LCUser>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(LCUser lcUser) {
                                lcUser.put("username", usernameText.getText().toString());
                                lcUser.put("mobilePhoneNumber", phoneText.getText().toString());
                                Log.d("lcuser", lcUser.getUsername());

                                if (!usernameText.getText().toString().isEmpty()) {
                                    if (phoneText.getText().toString().length() == 11) {
                                        lcUser.saveInBackground().subscribe(new Observer<LCObject>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(LCObject lcObject) {
                                                currentUser = lcUser;
                                                LCUser.changeCurrentUser(lcUser, true);
                                                Toast.makeText(getContext(), R.string.accountSet_ok, Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.d("account set error", e.getLocalizedMessage());
                                                displayError(e.getLocalizedMessage());
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                                        usernameText.setEnabled(false);
                                        phoneText.setEnabled(false);
                                        saveBtn.setVisibility(View.GONE);
                                    } else {
                                        infoText.setText(R.string.register_error_phone);
                                    }
                                } else {
                                    infoText.setText(R.string.accountSet_error_username);
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("account set error", e.getLocalizedMessage());
                                displayError(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCUser.requestPasswordResetInBackground(currentUser.getEmail())
                        .subscribe(new Observer<LCNull>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(LCNull lcNull) {
                                Log.d("success", "ok");
                                Snackbar.make(v, getText(R.string.accountSet_resetLabel), Snackbar.LENGTH_SHORT).show();
                                timer();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("reset", e.getLocalizedMessage());
                                displayError(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    private void timer() {
        new CountDownTimer(countTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (resetPass != null) {
                    resetPass.setClickable(false);
                    resetPass.setEnabled(false);
                    resetPass.setText(getText(R.string.reset_timerLabel) + String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (resetPass != null) {
                    resetPass.setClickable(true);
                    resetPass.setEnabled(true);
                    resetPass.setText(R.string.accountSet_password2);
                }
                cancel();
            }
        }.start();
    }

    private void displayError(String error) {
        switch (error) {
            case "Username has already been taken.":
                infoText.setText(R.string.register_error_username);
            case "Too many emails have been sent to the same email address.":
                infoText.setText(R.string.accountSet_error_reset);
            default:
                infoText.setText(error);
        }
    }


}
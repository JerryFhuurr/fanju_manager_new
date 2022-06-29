package com.jerry.fanju_manager.main.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jerry.fanju_manager.R;

import cn.leancloud.LCUser;
import cn.leancloud.types.LCNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ResetPasswordFragment extends Fragment {

    private EditText emailReset;
    private TextView resetInfo;
    private Button sendBtn;
    private ImageButton backBtn;
    private final int countTimer = 60000;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);
        emailReset = v.findViewById(R.id.reset_email);
        resetInfo = v.findViewById(R.id.reset_info);
        sendBtn = v.findViewById(R.id.reset_sendBtn);
        backBtn = v.findViewById(R.id.reset_backLogin);

        setUpView();
        return v;
    }

    private void setUpView(){
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCUser.requestPasswordResetInBackground(emailReset.getText().toString())
                        .subscribe(new Observer<LCNull>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(LCNull lcNull) {
                                Log.d("success", "ok");
                                timer();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("reset", e.getLocalizedMessage());
                                resetInfo.setText(R.string.reset_error);
                                resetInfo.setTextColor(getResources().getColor(R.color.r_red));
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_resetPasswordFragment_to_loginFragment);
            }
        });

    }

    private void timer(){
        new CountDownTimer(countTimer, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                if (sendBtn != null){
                    sendBtn.setClickable(false);
                    sendBtn.setEnabled(false);
                    resetInfo.setText(getText(R.string.reset_timerLabel) + String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (sendBtn != null){
                    sendBtn.setClickable(true);
                    sendBtn.setEnabled(true);
                    resetInfo.setText("");
                }
                cancel();
            }
        }.start();
    }
}
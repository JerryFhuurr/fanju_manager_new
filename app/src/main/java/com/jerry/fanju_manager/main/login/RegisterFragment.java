package com.jerry.fanju_manager.main.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.main.handler.DataHandler;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterFragment extends Fragment {

    private TextView backLogin;
    private EditText usernameEdit;
    private EditText emailEdit;
    private EditText passwordEdit1;
    private EditText passwordEdit2;
    private EditText phoneText;
    private TextView registerInfo;
    private Button registerBtn;

    public RegisterFragment() {
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        backLogin = v.findViewById(R.id.register_backLogin);
        usernameEdit = v.findViewById(R.id.register_name);
        emailEdit = v.findViewById(R.id.register_email);
        passwordEdit1 = v.findViewById(R.id.register_password1);
        passwordEdit2 = v.findViewById(R.id.register_password2);
        phoneText = v.findViewById(R.id.register_phone);
        registerInfo = v.findViewById(R.id.register_info);
        registerBtn = v.findViewById(R.id.registerBtn);

        setUpView();
        return v;
    }

    private void setUpView() {
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password1 = passwordEdit1.getText().toString();
                String password2 = passwordEdit2.getText().toString();
                String phone = phoneText.getText().toString();
                boolean isValid = verifyInfo(username, email, password1, password2, phone);

                if (isValid) {
                    registerInfo.setText("");
                    register(username, email, password1, phone, v);
                }
            }
        });
    }

    private boolean verifyInfo(String username, String email, String p1, String p2, String phone) {
        if (!username.isEmpty() && !email.isEmpty()) {
            if (DataHandler.emailValid(email)) {
                if (p1.equals(p2)) {
                    if (p1.length() >= 6) {
                        if ((!phone.isEmpty() && phone.length() == 11) || phone.isEmpty()) {  //填写手机号
                            return true;
                        } else if (!phone.isEmpty() && phone.length() != 11) {  //手机号不正确
                            registerInfo.setText(R.string.register_error_phone);
                            return false;
                        }
                    } else {
                        registerInfo.setText(R.string.register_error_password1);
                        return false;
                    }
                } else {
                    registerInfo.setText(R.string.register_error_password2);
                    return false;
                }
            } else {
                registerInfo.setText(R.string.register_error_email);
                return false;
            }
        } else {
            registerInfo.setText(R.string.register_error_empty);
            return false;
        }
        return false;
    }

    private void register(String username, String email, String p1, String phone, View v) {
        LCUser lcUser = new LCUser();
        lcUser.setUsername(username);
        lcUser.setPassword(p1);
        lcUser.setEmail(email);
        if (!phone.isEmpty()) {
            lcUser.setMobilePhoneNumber(phone);
        }

        lcUser.signUpInBackground().subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LCUser lcUser) {
                Toast.makeText(getContext(), R.string.register_ok, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("register error", e.getLocalizedMessage());
                String error = e.getLocalizedMessage();
                displayErrorInCN(error);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void displayErrorInCN(String error){
        switch (error){
            case "The email is already taken by another user.":
                registerInfo.setText(R.string.register_error_username);
                break;
            case "Username has already been taken.":
                registerInfo.setText(R.string.register_error_sameEmail);
                break;
            default:
                registerInfo.setText(error);
        }
    }
}
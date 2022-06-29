package com.jerry.fanju_manager.main.login;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jerry.fanju_manager.R;
import com.jerry.fanju_manager.main.handler.DataHandler;
import com.jerry.fanju_manager.main.model.User;
import com.jerry.fanju_manager.main.viewmodel.LoginViewModel;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;

    private EditText emailUsername;
    private EditText passwordText;
    private TextView logout;
    private TextView loginInfo;
    private Button loginBtn;
    private TextView toRegister;

    public LoginFragment() {
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        emailUsername = v.findViewById(R.id.email_login);
        passwordText = v.findViewById(R.id.password_login);
        logout = v.findViewById(R.id.login_out);
        loginBtn = v.findViewById(R.id.login_button);
        loginInfo = v.findViewById(R.id.login_error);
        toRegister = v.findViewById(R.id.login_register);

        setupView();
        return v;
    }

    private void setupView() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                String username;
                String password = passwordText.getText().toString();

                if (!emailUsername.getText().toString().isEmpty() && !password.isEmpty()) {
                    if (DataHandler.emailValid(emailUsername.getText().toString())) {
                        email = emailUsername.getText().toString();
                        login(null, password, email);
                    } else {
                        username = emailUsername.getText().toString();
                        login(username, password, null);
                    }
                } else {
                    loginInfo.setText(R.string.login_error_empty);
                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.logOut();
            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    private void login(String username, String password, String email) {
        if (username != null && email == null) {
            LCUser.logIn(username, password).subscribe(new Observer<LCUser>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(LCUser lcUser) {
                    Log.i(TAG, "Login Successfully!");
                    loginViewModel.login(lcUser);
                    loginInfo.setText(R.string.login_ok);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, e.toString());
                    displayErrorInCN(e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        } else if (username == null && email != null) {
            LCUser.loginByEmail(email, password).subscribe(new Observer<LCUser>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(LCUser lcUser) {
                    Log.i(TAG, "Login Successfully!");
                    loginViewModel.login(lcUser);
                    loginInfo.setText(R.string.login_ok);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, e.toString());
                    displayErrorInCN(e.getLocalizedMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        } else if (username == null && email == null) {
            loginInfo.setText(R.string.login_error_empty);
        }
    }

    private void displayErrorInCN(String message) {
        switch (message) {
            case "Could not find user.":
                loginInfo.setText(R.string.login_error_email2);
                break;
            case "The username and password mismatch.":
                loginInfo.setText(R.string.login_error_password);
                break;
            case "Email address isn't verified.":
                loginInfo.setText(R.string.login_error_email);
                break;
            case "You have entered incorrect passwords for too many times. Please try later or reset your password.":
                loginInfo.setText(R.string.login_error_password2);
                break;
            default:
                loginInfo.setText(message);
        }
    }
}
package io.develotex.comma_sample.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import io.develotex.comma_sample.R;
import io.develotex.comma_sample.ui.BaseFragment;


public class AuthFragment extends BaseFragment {

    private View mView;
    private EditText mUserIdEditText;
    private EditText mUserNameEditText;

    private Button mConfirmButton;

    private AuthViewModel mViewModel;

    public AuthFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_auth, container, false);

        initViews();

        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        return mView;
    }

    private void initViews() {
        mUserIdEditText = mView.findViewById(R.id.fragment_auth_user_id_edit_text);
        mUserNameEditText = mView.findViewById(R.id.fragment_auth_user_name_edit_text);

        mConfirmButton = mView.findViewById(R.id.fragment_auth_confirm_button);
        mConfirmButton.setOnClickListener(view -> {
            String userId = mUserIdEditText.getText().toString();
            String userName = mUserNameEditText.getText().toString();

            if(!userId.isEmpty() && !userName.isEmpty()) {
                mViewModel.saveCredentials(userId, userName);
                getNavigation().popBackStack();
            } else Toast.makeText(getContext(), getString(R.string.fragment_auth_fill_fields), Toast.LENGTH_LONG).show();
        });
    }

}
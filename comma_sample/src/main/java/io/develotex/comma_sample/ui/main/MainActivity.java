package io.develotex.comma_sample.ui.main;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import io.develotex.comma_sample.R;


public class MainActivity extends AppCompatActivity {

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);


        initView();

    }

    private void initView() {


    }

}
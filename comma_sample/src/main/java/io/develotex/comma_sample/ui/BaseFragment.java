package io.develotex.comma_sample.ui;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {}

    public NavController getNavigation() {
        return NavHostFragment.findNavController(this);
    }

}
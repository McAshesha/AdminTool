package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;

import static ru.ashesha.admintool.utils.Utils.*;

public class MainFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNowView(view);
        NavController controller = findNavController();
        view.findViewById(R.id.topButton).setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_topFragment));
        view.findViewById(R.id.settingsButton).setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_settingsFragment));
        view.findViewById(R.id.onlineButton).setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_onlineFragment));
        view.findViewById(R.id.adminButton).setOnClickListener(v -> controller.navigate(R.id.action_mainFragment_to_adminFragment));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isInvisibleDeepView())
            findDeepNavController().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
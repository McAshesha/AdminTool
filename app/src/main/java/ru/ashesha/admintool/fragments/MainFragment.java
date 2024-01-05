package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnClickListenerWithSound;


public class MainFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowView(view);
        NavController controller = device.findNavController();
        view.findViewById(R.id.topButton).setOnClickListener((OnClickListenerWithSound) v -> controller.navigate(R.id.action_mainFragment_to_topFragment));
        view.findViewById(R.id.settingsButton).setOnClickListener((OnClickListenerWithSound) v -> controller.navigate(R.id.action_mainFragment_to_settingsFragment));
        view.findViewById(R.id.onlineButton).setOnClickListener((OnClickListenerWithSound) v -> controller.navigate(R.id.action_mainFragment_to_onlineFragment));
        view.findViewById(R.id.adminButton).setOnClickListener((OnClickListenerWithSound) v -> controller.navigate(R.id.action_mainFragment_to_adminFragment));
    }

    @Override
    public void onStart() {
        super.onStart();
        Device device = Device.getInstance();
        if (!device.isNowMenuViewHidden())
            device.findMenuNavController().popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
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
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnClickListenerWithSound;


public class AdminFragment extends Fragment {

    //TODO: Включить работоспособность кнопок
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowView(view);
        NavController controller = device.findNavController();


        view.findViewById(R.id.back).setOnClickListener(v -> controller.popBackStack());

        view.findViewById(R.id.complaint).setOnClickListener((OnClickListenerWithSound) l -> {
            NavController adminController = device.findAdminNavController();
            if (!device.isNowAdminViewHidden())
                adminController.popBackStack();
            adminController.navigate(R.id.action_adminEmptyFragment_to_complaintFragment);
        });

        view.findViewById(R.id.ban).setOnClickListener((OnClickListenerWithSound) l -> {
            NavController adminController = device.findAdminNavController();
            if (!device.isNowAdminViewHidden())
                adminController.popBackStack();
            adminController.navigate(R.id.action_adminEmptyFragment_to_banFragment);
        });

        view.findViewById(R.id.unban).setOnClickListener((OnClickListenerWithSound) l -> {
            NavController adminController = device.findAdminNavController();
            if (!device.isNowAdminViewHidden())
                adminController.popBackStack();
            adminController.navigate(R.id.action_adminEmptyFragment_to_unbanFragment);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Device device = Device.getInstance();
        NavController adminController = device.findAdminNavController();
        if (!device.isNowAdminViewHidden())
            adminController.popBackStack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
}
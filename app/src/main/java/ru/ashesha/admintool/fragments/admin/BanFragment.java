package ru.ashesha.admintool.fragments.admin;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.utils.Device;

public class BanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ban, container, false);
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);
    }

}
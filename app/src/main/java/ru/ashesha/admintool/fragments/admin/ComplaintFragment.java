package ru.ashesha.admintool.fragments.admin;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnItemSelectedWithSound;

public class ComplaintFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);

        Spinner cause = view.findViewById(R.id.cause), count = view.findViewById(R.id.count);
        EditText other = view.findViewById(R.id.other), nick = view.findViewById(R.id.nick);

        cause.setOnItemSelectedListener((OnItemSelectedWithSound) (v, position, id) -> {
            if (position == 4)
                other.setVisibility(View.VISIBLE);
            else other.setVisibility(View.INVISIBLE);
        });

        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaint, container, false);
    }
}
package ru.ashesha.admintool.fragments.admin;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
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
import ru.ashesha.admintool.utils.Device.OnTextChangeListener;
import ru.ashesha.admintool.utils.Utils.Method;

import java.util.function.Consumer;

public class ComplaintFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);

        int lengthArrayCause = device.getResources().getStringArray(R.array.cause).length;

        Spinner cause = view.findViewById(R.id.cause), count = view.findViewById(R.id.count);
        EditText other = view.findViewById(R.id.other), nick = view.findViewById(R.id.nick);
        Button give = view.findViewById(R.id.give);

        Method method = () -> {
            if (!nick.getText().toString().isEmpty() &&
                    (cause.getSelectedItemPosition() != lengthArrayCause - 1 ||
                            cause.getSelectedItemPosition() == lengthArrayCause - 1 &&
                                    !other.getText().toString().isEmpty())) {
                give.setVisibility(View.VISIBLE);
                give.setClickable(true);
            } else {
                give.setVisibility(View.INVISIBLE);
                give.setClickable(false);
            }
        };

        cause.setOnItemSelectedListener((OnItemSelectedWithSound) (v, position, id) -> {
            if (position == lengthArrayCause - 1)
                other.setVisibility(View.VISIBLE);
            else other.setVisibility(View.INVISIBLE);
            method.apply();
        });

        nick.addTextChangedListener((OnTextChangeListener) l -> method.apply());
        other.addTextChangedListener((OnTextChangeListener) l -> method.apply());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaint, container, false);
    }
}
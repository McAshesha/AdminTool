package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.utils.UserData;

import static ru.ashesha.admintool.utils.Utils.findNavController;
import static ru.ashesha.admintool.utils.Utils.setNowView;

public class SettingsFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNowView(view);
        NavController controller = findNavController();
        view.findViewById(R.id.back).setOnClickListener(v -> controller.popBackStack());

        EditText topLogin = view.findViewById(R.id.topLogin), topPassword = view.findViewById(R.id.topPassword),
                onlineLogin = view.findViewById(R.id.onlineLogin), onlinePassword = view.findViewById(R.id.onlinePassword),
                adminLogin = view.findViewById(R.id.adminLogin), adminPassword = view.findViewById(R.id.adminPassword),
                automessage = view.findViewById(R.id.automessage);

        Switch enableAutomessage = view.findViewById(R.id.enableAutomessage);

        enableAutomessage.setChecked(UserData.enableAutomessage);
        topLogin.setText(UserData.topLogin);
        topPassword.setText(UserData.topPassword);
        onlineLogin.setText(UserData.onlineLogin);
        onlinePassword.setText(UserData.onlinePassword);
        adminLogin.setText(UserData.adminLogin);
        adminPassword.setText(UserData.adminPassword);
        automessage.setText(UserData.automessage);

        enableAutomessage.setOnCheckedChangeListener((li, i) -> UserData.enableAutomessage = li.isChecked());
        topLogin.addTextChangedListener((TextListener) s -> UserData.topLogin = s.toString());
        topPassword.addTextChangedListener((TextListener) s -> UserData.topPassword = s.toString());
        onlineLogin.addTextChangedListener((TextListener) s -> UserData.onlineLogin = s.toString());
        onlinePassword.addTextChangedListener((TextListener) s -> UserData.onlinePassword = s.toString());
        adminLogin.addTextChangedListener((TextListener) s -> UserData.adminLogin = s.toString());
        adminPassword.addTextChangedListener((TextListener) s -> UserData.adminPassword = s.toString());
        automessage.addTextChangedListener((TextListener) s -> UserData.automessage = s.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    interface TextListener extends TextWatcher {
        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

}
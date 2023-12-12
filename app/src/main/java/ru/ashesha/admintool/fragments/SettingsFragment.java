package ru.ashesha.admintool.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnTextChangeListener;
import ru.ashesha.admintool.utils.UserData;

public class SettingsFragment extends Fragment {

    /**
     * TODO: Добавить билдер пакетов
     * TODO: Добавить кнопку выключения звука
     */

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowView(view);
        NavController controller = device.findNavController();
        view.findViewById(R.id.back).setOnClickListener(v -> controller.popBackStack());

        UserData data = UserData.getInstance();
        EditText topLogin = view.findViewById(R.id.topLogin), topPassword = view.findViewById(R.id.topPassword),
                onlineLogin = view.findViewById(R.id.onlineLogin), onlinePassword = view.findViewById(R.id.onlinePassword),
                adminLogin = view.findViewById(R.id.adminLogin), adminPassword = view.findViewById(R.id.adminPassword),
                automessage = view.findViewById(R.id.automessage), version = view.findViewById(R.id.version);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch enableAutomessage = view.findViewById(R.id.enableAutomessage);

        version.setText(data.getRealVersion());
        enableAutomessage.setChecked(data.isRealEnableAutomessage());
        topLogin.setText(data.getRealTopLogin());
        topPassword.setText(data.getRealTopPassword());
        onlineLogin.setText(data.getRealOnlineLogin());
        onlinePassword.setText(data.getRealOnlinePassword());
        adminLogin.setText(data.getRealAdminLogin());
        adminPassword.setText(data.getRealAdminPassword());
        automessage.setText(data.getRealAutomessage());

        version.addTextChangedListener((OnTextChangeListener) s -> data.setVersion(s.toString()));
        enableAutomessage.setOnCheckedChangeListener((li, i) -> data.setEnableAutomessage(li.isChecked()));
        topLogin.addTextChangedListener((OnTextChangeListener) s -> data.setTopLogin(s.toString()));
        topPassword.addTextChangedListener((OnTextChangeListener) s -> data.setTopPassword(s.toString()));
        onlineLogin.addTextChangedListener((OnTextChangeListener) s -> data.setOnlineLogin(s.toString()));
        onlinePassword.addTextChangedListener((OnTextChangeListener) s -> data.setOnlinePassword(s.toString()));
        adminLogin.addTextChangedListener((OnTextChangeListener) s -> data.setAdminLogin(s.toString()));
        adminPassword.addTextChangedListener((OnTextChangeListener) s -> data.setAdminPassword(s.toString()));
        automessage.addTextChangedListener((OnTextChangeListener) s -> data.setAutomessage(s.toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
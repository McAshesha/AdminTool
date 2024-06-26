package ru.ashesha.admintool.fragments.menu;

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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class MenuTopFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowMenuView(view);
        device.setNowMenuViewHidden(false);
        NavController controller = device.findMenuNavController();

        List<String> lines = device.getDataModel().getInfo("top");

        Consumer<Integer> copyPlace = integer -> {
            controller.popBackStack();
            String line = lines.get(integer).substring(3);
            device.copyTextToClipboard(line);
            device.showToastBar("Скопировано в буфер обмена!");
        };

        view.findViewById(R.id.firstButton).setOnClickListener(v -> copyPlace.accept(0));
        view.findViewById(R.id.secondButton).setOnClickListener(v -> copyPlace.accept(1));
        view.findViewById(R.id.thirdButton).setOnClickListener(v -> copyPlace.accept(2));
        view.findViewById(R.id.fourthButton).setOnClickListener(v -> copyPlace.accept(3));
        view.findViewById(R.id.fifthButton).setOnClickListener(v -> copyPlace.accept(4));
        view.findViewById(R.id.allButton).setOnClickListener(v -> {
            controller.popBackStack();
            StringBuilder builder = new StringBuilder();
            lines.forEach(builder::append);
            device.copyTextToClipboard(builder.toString());
            device.showToastBar("Скопировано в буфер обмена!");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_top, container, false);
    }
}
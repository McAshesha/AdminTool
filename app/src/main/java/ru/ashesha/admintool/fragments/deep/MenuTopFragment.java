package ru.ashesha.admintool.fragments.deep;

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
import java.util.function.Function;


public class MenuTopFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowSmallView(view);
        device.setNowSmallViewHidden(false);
        NavController controller = device.findSmallNavController();

        List<String> lines = device.getDataModel().getInfo("top");

        Function<Integer, String> getPlace = (integer) -> {
            controller.popBackStack();
            return lines.get(integer).substring(3);
        };
        StringBuilder builder = new StringBuilder();
        lines.forEach(builder::append);

        view.findViewById(R.id.firstButton).setOnClickListener(v -> device.copyTextToClipboard(getPlace.apply(0)));
        view.findViewById(R.id.secondButton).setOnClickListener(v -> device.copyTextToClipboard(getPlace.apply(1)));
        view.findViewById(R.id.thirdButton).setOnClickListener(v -> device.copyTextToClipboard(getPlace.apply(2)));
        view.findViewById(R.id.fourthButton).setOnClickListener(v -> device.copyTextToClipboard(getPlace.apply(3)));
        view.findViewById(R.id.fifthButton).setOnClickListener(v -> device.copyTextToClipboard(getPlace.apply(4)));
        view.findViewById(R.id.allButton).setOnClickListener(v -> {
            controller.popBackStack();
            device.copyTextToClipboard(builder.toString());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_top, container, false);
    }
}
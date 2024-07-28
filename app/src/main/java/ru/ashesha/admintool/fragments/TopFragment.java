package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.mo.Decoder;
import ru.ashesha.admintool.mo.MafiaConnection;
import ru.ashesha.admintool.mo.packets.client.Login;
import ru.ashesha.admintool.mo.packets.client.OnlineFriend;
import ru.ashesha.admintool.mo.packets.client.Top;
import ru.ashesha.admintool.mo.packets.server.ResultLogin;
import ru.ashesha.admintool.mo.packets.server.ResultOnlineFriend;
import ru.ashesha.admintool.mo.packets.server.ResultTop;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.ashesha.admintool.utils.Utils.EXECUTOR;
import static ru.ashesha.admintool.utils.Utils.sleep;

public class TopFragment extends Fragment {

    private TextView title, list;
    private EditText searchNick;
    private LinearLayout layoutSearch;

    private List<String> lines;
    private MafiaConnection connection;


    @Override
    public void onDestroy() {
        super.onDestroy();
        Device.getInstance().getDataModel().removeInfo("top");
        connection = null;
        lines = null;
        title = null;
        list = null;
        searchNick = null;
        layoutSearch = null;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowView(view);

        NavController controller = device.findNavController();
        title = view.findViewById(R.id.title);
        list = view.findViewById(R.id.listTop);
        searchNick = view.findViewById(R.id.searchNick);
        layoutSearch = view.findViewById(R.id.layoutSearch);

        view.findViewById(R.id.back).setOnClickListener(v -> controller.popBackStack());
        searchNick.addTextChangedListener((Device.OnTextChangeListener) str -> {
            StringBuilder builder = new StringBuilder();
            List<String> result = lines.stream().filter(line -> line.contains(str)).collect(Collectors.toList());
            result.forEach(builder::append);
            device.getDataModel().putInfo("top", result.subList(0, Math.min(50, result.size())));
            list.setText(builder);
        });

        list.setOnClickListener(v -> {
            NavController menuController = device.findMenuNavController();
            if (device.isNowMenuViewHidden())
                menuController.navigate(R.id.action_invisibleFragment_to_menuTopFragment);
            else menuController.popBackStack();
        });

        EXECUTOR.execute(this::sendRequestListTop);
        EXECUTOR.execute(() -> {
            sleep(10_000);
            if (layoutSearch == null || searchNick == null || list == null || title == null || !list.getText().toString().isEmpty())
                return;
            error();
        });
    }

    private void sendRequestListTop() {
        connection = new MafiaConnection("http://mafiaonline.jcloud.kz");
        try {
            connection.connect();
        } catch (MafiaConnection.NotConnectedException ignored) {
            error();
            return;
        }

        connection.registerListenerPacket(ResultLogin.class, packet -> {
            if (packet != null) {
                Decoder.lfm = packet.wait;
                connection.sendPacket(new Top());
            } else error();
        });

        connection.registerListenerPacket(ResultTop.class, packet -> {
            if (packet != null) {
                lines = new ArrayList<>();
                OnlineFriend packetOnline = new OnlineFriend();

                for (int i = 1; i <= 1000; i++) {
                    Map.Entry<String, Integer> entry = packet.top.get(i - 1);
                    lines.add(i + ". " + entry.getKey() + " " + entry.getValue() + " - ");
                    packetOnline.addNick(entry.getKey());
                }

                connection.sendPacket(packetOnline);
            } else error();
        });

        connection.registerListenerPacket(ResultOnlineFriend.class, packet -> {
            if (packet != null) {
                connection.disconnect();

                StringBuilder result = new StringBuilder();
                for (int i = 0; i < 1000; i++) {
                    String online = packet.onlines.get(i) ? "онлайн\n" : "оффлайн\n";
                    lines.set(i, lines.get(i) + online);
                    result.append(lines.get(i));
                }

                Device device = Device.getInstance();
                device.getDataModel().putInfo("top", new ArrayList<>(lines).subList(0, 50));
                device.runOnMainThread(() -> {
                    title.setText("Список топа игроков");
                    list.setText(result);
                    list.setClickable(true);
                    layoutSearch.setVisibility(View.VISIBLE);
                });
            } else error();
        });

        UserData data = UserData.getInstance();
        connection.sendPacket(new  Login(data.getTopLogin(), data.getTopPassword(), data.getVersion()));
    }

    private void error() {
        if (connection != null)
            connection.disconnect();
        Device device = Device.getInstance();
        device.getDataModel().removeInfo("top");
        device.runOnMainThread(() -> {
            title.setText("☢Произошла ошибка☢ ;(");
            list.setText("");
            list.setClickable(false);
            layoutSearch.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }
}
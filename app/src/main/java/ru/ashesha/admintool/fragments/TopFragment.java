package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.ashesha.admintool.utils.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.ashesha.admintool.utils.Utils.*;

public class TopFragment extends Fragment {

    private TextView title, list;

    private static List<String> lines;
    private MafiaConnection connection;


    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    private void pause() {
        if (connection != null)
            connection.disconnect();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setNowView(view);
        NavController controller = findNavController();
        title = view.findViewById(R.id.title);
        list = view.findViewById(R.id.listTop);
        TextView back = view.findViewById(R.id.back);

        back.setOnClickListener(v -> controller.popBackStack());

        list.setOnClickListener(v -> {
            NavController deepController = findDeepNavController();
            if (isInvisibleDeepView())
                deepController.navigate(R.id.action_invisibleFragment_to_menuTopFragment);
            else deepController.popBackStack();
        });

        EXECUTOR.execute(this::requestListTop);
        EXECUTOR.execute(() -> {
            sleep(10_000);
            if (!list.getText().toString().isEmpty())
                return;
            if (connection != null)
                connection.disconnect();
            error();
        });
    }

    private void requestListTop() {
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
            if (packet == null) {
                error();
                return;
            }

            lines = new ArrayList<>();
            OnlineFriend packetOnline = new OnlineFriend();

            for (int i = 1; i <= 20; i ++) {
                Map.Entry<String, Integer> entry = packet.top.get(i - 1);
                lines.add(i + ". " + entry.getKey() + " " + entry.getValue() + " - ");
                packetOnline.addNick(entry.getKey());
            }

            connection.sendPacket(packetOnline);
        });

        connection.registerListenerPacket(ResultOnlineFriend.class, packet -> {
            connection.disconnect();

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 20; i ++) {
                String online = packet.onlines.get(i) ? "онлайн\n" : "оффлайн\n";
                lines.set(i, lines.get(i) + online);
                result.append(lines.get(i));
            }

            runOnMainThread(() -> {
                title.setText("Список топа игроков:");
                list.setText(result);
                list.setClickable(true);
            });
        });

        String topLogin = UserData.topLogin.isEmpty() ? getResources().getString(R.string.topLogin) : UserData.topLogin,
                topPassword = UserData.topPassword.isEmpty() ? getResources().getString(R.string.topPassword) : UserData.topPassword;
        connection.sendPacket(new  Login(topLogin, topPassword));
    }

    private void error() {
        pause();
        runOnMainThread(() -> title.setText("☢Произошла ошибка☢ ;("));
    }

    public static List<String> getLines() {
        return lines;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }
}
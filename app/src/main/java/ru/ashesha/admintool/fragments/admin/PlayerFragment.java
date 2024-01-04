package ru.ashesha.admintool.fragments.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.mo.Decoder;
import ru.ashesha.admintool.mo.MafiaConnection;
import ru.ashesha.admintool.mo.packets.client.EnterRegionChat;
import ru.ashesha.admintool.mo.packets.client.Login;
import ru.ashesha.admintool.mo.packets.client.SendMessageToRegionChat;
import ru.ashesha.admintool.mo.packets.server.NewMessageRegionChat;
import ru.ashesha.admintool.mo.packets.server.ResultLogin;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.UserData;

import static ru.ashesha.admintool.utils.Utils.*;


public class PlayerFragment extends Fragment {

    private MafiaConnection connection;
    private TextView resultInfo;
    private Button get;
    private EditText nick;


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);

        nick = view.findViewById(R.id.nick);
        get = view.findViewById(R.id.get);
        resultInfo = view.findViewById(R.id.resultInfo);

        nick.addTextChangedListener((Device.OnTextChangeListener) l -> {
            if (nick.getText().toString().isEmpty()) {
                get.setVisibility(View.INVISIBLE);
                get.setClickable(false);
            } else {
                get.setVisibility(View.VISIBLE);
                get.setClickable(true);
            }
            resultInfo.setText("");
        });

        get.setOnClickListener((Device.OnClickListenerWithSound) l -> {
            get.setVisibility(View.INVISIBLE);
            get.setClickable(false);
            resultInfo.setText("");
            EXECUTOR.execute(this::sendRequest);
            EXECUTOR.execute(() -> {
                sleep(10_000);
                if (connection == null || resultInfo == null || get == null || nick == null || !connection.connected() || !resultInfo.getText().toString().isEmpty())
                    return;
                error();
            });
        });
    }

    private void sendRequest() {
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
                connection.sendPacket(new EnterRegionChat());
                sleep(100);
                String command = String.format("[%s]-/инфо", nick.getText());
                System.out.println(command);
                connection.sendPacket(new SendMessageToRegionChat(command, ""));
            } else error();
        });

        connection.registerListenerPacket(NewMessageRegionChat.class, packet -> {
            if (packet == null || packet.author == null || !packet.author.equals("Admin") && !packet.author.equals("admin"))
                return;
            connection.disconnect();
            Device device = Device.getInstance();
            device.runOnMainThread(() -> {
                resultInfo.setText(formatInfoPlayer(packet.message));
                get.setVisibility(View.VISIBLE);
                get.setClickable(true);
            });
        });

        UserData data = UserData.getInstance();
        connection.sendPacket(new Login(data.getAdminLogin(), data.getAdminPassword(), data.getVersion()));
    }

    private void error() {
        Device device = Device.getInstance();
        device.runOnMainThread(() -> {
            get.setVisibility(View.INVISIBLE);
            get.setClickable(false);
            nick.setText("");
            resultInfo.setText("☢Произошла ошибка☢ ;(");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connection != null)
            connection.disconnect();
        connection = null;
        resultInfo = null;
        get = null;
        nick = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }
}
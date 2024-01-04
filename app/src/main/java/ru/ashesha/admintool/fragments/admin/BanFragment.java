package ru.ashesha.admintool.fragments.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import ru.ashesha.admintool.utils.Utils;

import static ru.ashesha.admintool.utils.Utils.EXECUTOR;
import static ru.ashesha.admintool.utils.Utils.sleep;

public class BanFragment extends Fragment {

    private MafiaConnection connection;
    private TextView resultInfo;
    private Button ban;
    private EditText other, nick, room;
    private Spinner cause, period;
    private int lengthArrayCause;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);

        lengthArrayCause = device.getResources().getStringArray(R.array.cause).length;

        cause = view.findViewById(R.id.cause);
        room = view.findViewById(R.id.room);
        period = view.findViewById(R.id.period);
        other = view.findViewById(R.id.other);
        nick = view.findViewById(R.id.nick);
        ban = view.findViewById(R.id.ban);
        resultInfo = view.findViewById(R.id.resultInfo);

        Utils.Method method = () -> {
            if (!nick.getText().toString().isEmpty() && !room.getText().toString().isEmpty() &&
                    (cause.getSelectedItemPosition() != lengthArrayCause - 1 ||
                            cause.getSelectedItemPosition() == lengthArrayCause - 1 &&
                                    !other.getText().toString().isEmpty())) {
                ban.setVisibility(View.VISIBLE);
                ban.setClickable(true);
            } else {
                ban.setVisibility(View.INVISIBLE);
                ban.setClickable(false);
            }
            resultInfo.setText("");
        };

        cause.setOnItemSelectedListener((Device.OnItemSelectedWithSound) (v, position, id) -> {
            if (position == lengthArrayCause - 1)
                other.setVisibility(View.VISIBLE);
            else other.setVisibility(View.INVISIBLE);
            method.apply();
        });

        period.setOnItemSelectedListener((Device.OnItemSelectedWithSound) (v, position, id) -> method.apply());
        room.addTextChangedListener((Device.OnTextChangeListener) l -> method.apply());
        nick.addTextChangedListener((Device.OnTextChangeListener) l -> method.apply());
        other.addTextChangedListener((Device.OnTextChangeListener) l -> method.apply());

        ban.setOnClickListener((Device.OnClickListenerWithSound) l -> {
            ban.setVisibility(View.INVISIBLE);
            ban.setClickable(false);
            resultInfo.setText("");
            EXECUTOR.execute(this::sendRequest);
            EXECUTOR.execute(() -> {
                sleep(10_000);
                if (connection == null || resultInfo == null || ban == null || other == null || nick == null || cause == null || period == null || room == null || !connection.connected() || !resultInfo.getText().toString().isEmpty())
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
                String causeStr = cause.getSelectedItemPosition() == lengthArrayCause - 1 ? other.getText().toString() : cause.getSelectedItem().toString();
                String command = String.format("[%s]-/забанить Комната-%s Причина-%s Срок-%s", nick.getText(), room.getText(), causeStr, period.getSelectedItem().toString());
                System.out.println(command);
                connection.sendPacket(new SendMessageToRegionChat(command, ""));
                sleep(100);
                connection.disconnect();
                Device device = Device.getInstance();
                device.runOnMainThread(() -> {
                    resultInfo.setText("Игрок заблокирован!");
                    ban.setVisibility(View.VISIBLE);
                    ban.setClickable(true);
                });
            } else error();
        });

        /*connection.registerListenerPacket(NewMessageRegionChat.class, packet -> {
            if (packet == null || packet.author == null || !packet.author.equals("Admin"))
                return;
            connection.disconnect();
            Device device = Device.getInstance();
            device.runOnMainThread(() -> {
                resultInfo.setText(packet.message);
                ban.setVisibility(View.VISIBLE);
                ban.setClickable(true);
            });
        });*/

        UserData data = UserData.getInstance();
        connection.sendPacket(new Login(data.getAdminLogin(), data.getAdminPassword(), data.getVersion()));
    }

    private void error() {
        Device device = Device.getInstance();
        device.runOnMainThread(() -> {
            ban.setVisibility(View.INVISIBLE);
            ban.setClickable(false);
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
        ban = null;
        other = null;
        nick = null;
        cause = null;
        period = null;
        room = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ban, container, false);
    }

}
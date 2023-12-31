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
import ru.ashesha.admintool.mo.packets.client.OnlineFriend;
import ru.ashesha.admintool.mo.packets.client.SendMessageToRegionChat;
import ru.ashesha.admintool.mo.packets.server.NewMessageRegionChat;
import ru.ashesha.admintool.mo.packets.server.ResultLogin;
import ru.ashesha.admintool.mo.packets.server.ResultOnlineFriend;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnItemSelectedWithSound;
import ru.ashesha.admintool.utils.Device.OnTextChangeListener;
import ru.ashesha.admintool.utils.Device.OnClickListenerWithSound;
import ru.ashesha.admintool.utils.UserData;
import ru.ashesha.admintool.utils.Utils.Method;

import static ru.ashesha.admintool.utils.Utils.EXECUTOR;
import static ru.ashesha.admintool.utils.Utils.sleep;

public class ComplaintFragment extends Fragment {


    private MafiaConnection connection;
    private TextView resultInfo;
    private Button give;
    private EditText other, nick;
    private Spinner cause, count;
    private int lengthArrayCause;


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowAdminView(view);
        device.setNowAdminViewHidden(false);

        lengthArrayCause = device.getResources().getStringArray(R.array.cause).length;

        cause = view.findViewById(R.id.cause);
        count = view.findViewById(R.id.count);
        other = view.findViewById(R.id.other);
        nick = view.findViewById(R.id.nick);
        give = view.findViewById(R.id.give);
        resultInfo = view.findViewById(R.id.resultInfo);

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
            resultInfo.setText("");
        };

        cause.setOnItemSelectedListener((OnItemSelectedWithSound) (v, position, id) -> {
            if (position == lengthArrayCause - 1)
                other.setVisibility(View.VISIBLE);
            else other.setVisibility(View.INVISIBLE);
            method.apply();
        });

        count.setOnItemSelectedListener((OnItemSelectedWithSound) (v, position, id) -> method.apply());

        nick.addTextChangedListener((OnTextChangeListener) l -> method.apply());
        other.addTextChangedListener((OnTextChangeListener) l -> method.apply());

        give.setOnClickListener((OnClickListenerWithSound) l -> {
            give.setVisibility(View.INVISIBLE);
            give.setClickable(false);
            resultInfo.setText("");
            EXECUTOR.execute(this::request);
        });
    }

    private void request() {
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
                String command = String.format("[%s]-/жб %s Причина-%s", nick.getText(), count.getSelectedItem().toString(), causeStr);
                connection.sendPacket(new SendMessageToRegionChat(command, ""));
            } else error();
        });

        connection.registerListenerPacket(NewMessageRegionChat.class, packet -> {
            connection.disconnect();
            Device device = Device.getInstance();
            device.runOnMainThread(() -> {
                resultInfo.setText("Все успешно выданы!");
                give.setVisibility(View.VISIBLE);
                give.setClickable(true);
            });
        });

        UserData data = UserData.getInstance();
        connection.sendPacket(new Login(data.getAdminLogin(), data.getAdminPassword(), data.getVersion()));
    }

    private void error() {
        Device device = Device.getInstance();
        device.runOnMainThread(() -> {
            give.setVisibility(View.VISIBLE);
            give.setClickable(true);
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
        give = null;
        other = null;
        nick = null;
        cause = null;
        count = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complaint, container, false);
    }
}
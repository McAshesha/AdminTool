package ru.ashesha.admintool.fragments;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.mo.Decoder;
import ru.ashesha.admintool.mo.MafiaConnection;
import ru.ashesha.admintool.mo.packets.client.InviteFriendship;
import ru.ashesha.admintool.mo.packets.client.Login;
import ru.ashesha.admintool.mo.packets.client.MessageToFriend;
import ru.ashesha.admintool.mo.packets.client.OnlineFriend;
import ru.ashesha.admintool.mo.packets.server.FriendShipAccept;
import ru.ashesha.admintool.mo.packets.server.ResultLogin;
import ru.ashesha.admintool.mo.packets.server.ResultOnlineFriend;
import ru.ashesha.admintool.utils.UserData;

import static ru.ashesha.admintool.utils.Utils.*;

public class OnlineFragment extends Fragment {

    private EditText nick;
    private TextView online, wait, title;
    private Button checkOnline, addToFriend;
    private MafiaConnection connection;

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    private void pause() {
        if (connection != null)
            connection.disconnect();
        title.setText("Проверить онлайн игрока");
        online.setText("");
        wait.setText("");
        addToFriend.setVisibility(View.INVISIBLE);
        addToFriend.setClickable(false);
        checkOnline.setVisibility(View.VISIBLE);
        checkOnline.setClickable(true);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setNowView(view);
        NavController controller = findNavController();
        TextView back = view.findViewById(R.id.back);
        nick = view.findViewById(R.id.nick);
        checkOnline = view.findViewById(R.id.checkOnline);
        online = view.findViewById(R.id.online);
        wait = view.findViewById(R.id.wait);
        title = view.findViewById(R.id.title);
        addToFriend = view.findViewById(R.id.addToFriend);

        back.setOnClickListener(v -> controller.popBackStack());
        checkOnline.setOnClickListener(v -> {
            title.setText("Проверить онлайн игрока");
            if (nick.getText().toString().isEmpty())
                return;
            checkOnline.setVisibility(View.INVISIBLE);
            checkOnline.setClickable(false);
            EXECUTOR.execute(this::requestOnline);
        });

        addToFriend.setOnClickListener(v -> {
            wait.setText("Ждем ответ от игрока...");
            addToFriend.setVisibility(View.INVISIBLE);
            addToFriend.setClickable(false);
            EXECUTOR.execute(this::sendFriendRequest);
        });

        nick.addTextChangedListener((TextListener) s -> pause());
    }

    private void sendFriendRequest() {
        connection = new MafiaConnection("http://mafiaonline.jcloud.kz");
        try {
            connection.connect();
        } catch (MafiaConnection.NotConnectedException ignored) {
            error();
            return;
        }

        String onlineLogin = UserData.onlineLogin.isEmpty() ? getResources().getString(R.string.onlineLogin) : UserData.onlineLogin,
                onlinePassword = UserData.onlinePassword.isEmpty() ? getResources().getString(R.string.onlinePassword) : UserData.onlinePassword,
                message = UserData.automessage.isEmpty() ? getResources().getString(R.string.automessage) : UserData.automessage,
                version = UserData.version.isEmpty() ? getResources().getString(R.string.version) : UserData.version;

        String nickname = nick.getText().toString();

        connection.registerListenerPacket(ResultLogin.class, packet -> {
            if (packet != null) {
                Decoder.lfm = packet.wait;
                connection.sendPacket(new InviteFriendship(onlineLogin, nickname));
            } else error();
        });

        connection.registerListenerPacket(FriendShipAccept.class, packet -> {
            StringBuilder msg = new StringBuilder("Игрок принял заявку!");
            if (UserData.enableAutomessage) {
                connection.sendPacket(new MessageToFriend(onlineLogin, nickname, message));
                msg.append(" Отправлено автосообщение.");
                connection.disconnect();
            } else connection.disconnect();
            runOnMainThread(() -> wait.setText(msg));
        });

        connection.sendPacket(new Login(onlineLogin, onlinePassword, version));
    }

    private void requestOnline() {
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
                connection.sendPacket(new OnlineFriend(nick.getText().toString()));
            } else error();
        });

        connection.registerListenerPacket(ResultOnlineFriend.class, packet -> {
            connection.disconnect();
            runOnMainThread(() -> online.setText(packet.onlines.get(0) ? "Онлайн" : "Оффлайн"));
            sleep(3500);
            if (!packet.onlines.get(0))
                return;
            runOnMainThread(() -> {
                addToFriend.setClickable(true);
                addToFriend.setVisibility(View.VISIBLE);
            });
        });


        String onlineLogin = UserData.onlineLogin.isEmpty() ? getResources().getString(R.string.onlineLogin) : UserData.onlineLogin,
                onlinePassword = UserData.onlinePassword.isEmpty() ? getResources().getString(R.string.onlinePassword) : UserData.onlinePassword,
                version = UserData.version.isEmpty() ? getResources().getString(R.string.version) : UserData.version;
        connection.sendPacket(new Login(onlineLogin, onlinePassword, version));
    }

    private void error() {
        runOnMainThread(() -> {
            pause();
            title.setText("☢Произошла ошибка☢ ;(");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, container, false);
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
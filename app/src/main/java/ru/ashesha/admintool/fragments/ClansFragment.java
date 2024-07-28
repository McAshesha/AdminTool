package ru.ashesha.admintool.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import org.jetbrains.annotations.NotNull;
import ru.ashesha.admintool.R;
import ru.ashesha.admintool.mo.Decoder;
import ru.ashesha.admintool.mo.MafiaConnection;
import ru.ashesha.admintool.mo.packets.client.Login;
import ru.ashesha.admintool.mo.packets.client.getClansTop;
import ru.ashesha.admintool.mo.packets.server.ResultLogin;
import ru.ashesha.admintool.mo.packets.server.getClansTopResult;
import ru.ashesha.admintool.utils.Device;
import ru.ashesha.admintool.utils.Device.OnClickListenerWithSound;
import ru.ashesha.admintool.utils.Device.OnItemSelectedWithSound;
import ru.ashesha.admintool.utils.UserData;
import ru.ashesha.admintool.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.lang.Integer.parseInt;
import static ru.ashesha.admintool.utils.Utils.*;

public class ClansFragment extends Fragment {

    private MafiaConnection connection;
    private EditText feature;
    private TextView arrow, title;
    private Spinner sort;
    private LinearLayout layoutSort, layoutSearch;
    private TableLayout tableClans;

    private List<String[]> clans;
    private int multiplySort = 1, position = 0;

    private TextView[][] textTable;

    private final List<Comparator<String[]>> comparators = new ArrayList<>(Arrays.asList(
            (first, second) -> parseInt(second[2]) - parseInt(first[2]),
            (first, second) -> parseInt(second[3].split("/")[0]) - parseInt(first[3].split("/")[0]),
            (first, second) -> (second[6].isEmpty() ? 0 : 1) - ((first[6].isEmpty() ? 0 : 1)),
            (first, second) -> (second[7].isEmpty() ? 0 : 1) - ((first[7].isEmpty() ? 0 : 1))
    ));

    private final Comparator<String[]> comparatorNow = (first, second) -> {
        for (int i = position; i < comparators.size(); i ++) {
            int compare = comparators.get(i).compare(first, second);
            if (compare != 0)
                return compare * multiplySort;
        }
        for (int i = 0; i < position; i ++) {
            int compare = comparators.get(i).compare(first, second);
            if (compare != 0)
                return compare * multiplySort;
        }
        return 0;
    };



    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Device device = Device.getInstance();
        device.loadNowView(view);

        NavController controller = device.findNavController();
        view.findViewById(R.id.back).setOnClickListener(v -> controller.popBackStack());

        arrow = view.findViewById(R.id.arrow);
        sort = view.findViewById(R.id.sort);
        title = view.findViewById(R.id.title);
        layoutSort = view.findViewById(R.id.layoutSort);
        layoutSearch = view.findViewById(R.id.layoutSearch);
        tableClans = view.findViewById(R.id.tableClans);
        feature = view.findViewById(R.id.feature);

        feature.addTextChangedListener((Device.OnTextChangeListener) str -> updateTableClans());

        tableClans.setOnClickListener(v -> {
            NavController menuController = device.findMenuNavController();
            if (device.isNowMenuViewHidden())
                menuController.navigate(R.id.action_invisibleFragment_to_menuClansFragment);
            else menuController.popBackStack();
        });

        sort.setOnItemSelectedListener((OnItemSelectedWithSound) (v, position, id) -> {
            this.position = position;
            updateTableClans();
        });

        arrow.setOnClickListener((OnClickListenerWithSound) v -> {
            String sign = arrow.getText().equals("⬊") ? "⬈" : "⬊";
            arrow.setText(sign);
            multiplySort *= -1;
            updateTableClans();
        });

        EXECUTOR.execute(this::sendRequestList);
        EXECUTOR.execute(() -> {
            sleep(10_000);
            if (feature == null || tableClans == null || title == null || arrow == null || sort == null || layoutSearch == null || layoutSort == null || tableClans.getChildCount() != 0)
                return;
            error();
        });
    }

    private void sendRequestList() {
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
                connection.sendPacket(new getClansTop());
            } else error();
        });

        connection.registerListenerPacket(getClansTopResult.class, packet -> {
            if (packet != null) {
                connection.disconnect();
                this.clans = packet.clans;
                createTableClans();
                Device.getInstance().runOnMainThread(() -> {
                    updateTableClans();
                    title.setText("Информация по кланам");
                    arrow.setClickable(true);
                    tableClans.setVisibility(View.VISIBLE);
                    layoutSort.setVisibility(View.VISIBLE);
                    layoutSearch.setVisibility(View.VISIBLE);
                });
            } else error();
        });

        UserData data = UserData.getInstance();
        connection.sendPacket(new Login(data.getTopLogin(), data.getTopPassword(), data.getVersion()));
    }

    private void createTableClans() {
        if (clans == null)
            return;
        Device device = Device.getInstance();
        String[] array = device.getResources().getStringArray(R.array.tableClans);
        Typeface typeText = title.getTypeface();

        Function<String, TextView> textView = (value) -> {
            TextView view = new TextView(getContext());
            view.setTypeface(typeText);
            view.setRotation(RANDOM.nextFloat() * 1.5f - 0.75f);
            view.setRotationY(RANDOM.nextFloat() * 1.5f - 0.75f);
            view.setTextColor(Color.WHITE);
            view.setPadding(5, 5, 5, 5);
            view.setGravity(Gravity.CENTER);
            view.setTextSize(8);
            view.setText(value);
            return view;
        };
        Supplier<TableRow> tableRow = () -> {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            row.setRotation(RANDOM.nextFloat() - 0.5f);
            row.setRotationY(RANDOM.nextFloat() - 0.5f);
            return row;
        };

        textTable = new TextView[clans.size()][];

        device.runOnMainThread(() -> {
            tableClans.removeAllViews();
            TableRow row = tableRow.get();
            for (String value : array)
                row.addView(textView.apply(value));
            tableClans.addView(row);

            row = new TableRow(getContext());
            row.addView(new TextView(getContext()));
            tableClans.addView(row);

            for (int i = 0; i < textTable.length; i ++) {

                textTable[i] = new TextView[array.length];
                String[] clan = clans.get(i);
                row = tableRow.get();

                for (int j = 0; j < textTable[i].length; j ++) {
                    textTable[i][j] = textView.apply("");
                    row.addView(textTable[i][j]);

                    if (j == 5) {
                        int length = clan[j].length();
                        clan[j] = length == 0 ? "нет" : length == 5 ? "все" : "есть";
                    } else if (j == 6 || j == 7)
                        clan[j] = clan[j].isEmpty() ? "нет" : clan[j];
                }

                tableClans.addView(row);

            }
        });
    }

    private void updateTableClans() {
        if (clans == null || clans.isEmpty())
            return;

        Device device = Device.getInstance();
        List<String[]> clans = this.clans.stream().filter(clan -> Arrays.stream(clan)
                .anyMatch(feat -> feat.contains(feature.getText()))).sorted(comparatorNow).collect(Collectors.toList());
        List<String> dataClans = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < clans.size(); i ++) {

            String[] clan = clans.get(i);
            StringBuilder strRow = new StringBuilder();
            for (int j = 0; j < clan.length; j ++) {
                textTable[i][j].setText(clan[j]);
                strRow.append(clan[j] + " ");
            }
            if (count ++ < 10)
                dataClans.add(strRow.deleteCharAt(strRow.length() - 1) + "\n");

        }

        for (int i = count; i < textTable.length; i ++)
            for (int j = 0; j < textTable[i].length; j ++)
                textTable[i][j].setText("");

        device.getDataModel().putInfo("clans", dataClans);
        tableClans.setClickable(true);
    }

    private void error() {
        if (connection != null)
            connection.disconnect();
        Device device = Device.getInstance();
        device.getDataModel().removeInfo("clans");
        device.runOnMainThread(() -> {
            title.setText("☢Произошла ошибка☢ ;(");
            tableClans.setClickable(false);
            tableClans.removeAllViews();
            arrow.setClickable(false);
            layoutSort.setVisibility(View.INVISIBLE);
            tableClans.setVisibility(View.INVISIBLE);
            layoutSearch.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Device.getInstance().getDataModel().removeInfo("clans");
        connection = null;
        sort = null;
        arrow = null;
        layoutSort = null;
        layoutSearch = null;
        title = null;
        tableClans = null;
        clans = null;
        feature = null;
        textTable = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clans, container, false);
    }
}
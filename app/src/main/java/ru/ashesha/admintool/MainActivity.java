package ru.ashesha.admintool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.ashesha.admintool.utils.UserData;

import static ru.ashesha.admintool.utils.Utils.setNowActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        setNowActivity(this);

        SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);

        UserData.topLogin = sharedPreferences.getString("topLogin", "");
        UserData.topPassword = sharedPreferences.getString("topPassword", "");

        UserData.onlineLogin = sharedPreferences.getString("onlineLogin", "");
        UserData.onlinePassword = sharedPreferences.getString("onlinePassword", "");

        UserData.adminLogin = sharedPreferences.getString("adminLogin", "");
        UserData.adminPassword = sharedPreferences.getString("adminPassword", "");

        UserData.automessage = sharedPreferences.getString("automessage", "");
        UserData.enableAutomessage = Boolean.parseBoolean(sharedPreferences.getString("enableAutomessage", "false"));

        UserData.version = sharedPreferences.getString("version", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("topLogin", UserData.topLogin);
        editor.putString("topPassword", UserData.topPassword);

        editor.putString("onlineLogin", UserData.onlineLogin);
        editor.putString("onlinePassword", UserData.onlinePassword);

        editor.putString("adminLogin", UserData.adminLogin);
        editor.putString("adminPassword", UserData.adminPassword);

        editor.putString("automessage", UserData.automessage);
        editor.putString("enableAutomessage", Boolean.toString(UserData.enableAutomessage));

        editor.putString("version", UserData.version);

        editor.apply();
    }
}

package ru.ashesha.admintool.utils;

import ru.ashesha.admintool.R;

import static ru.ashesha.admintool.utils.Utils.getResources;

public class UserData {

    private String topLogin = "", topPassword = "";
    private String onlineLogin = "", onlinePassword = "";
    private String adminLogin = "", adminPassword = "";
    private String automessage = "";
    private boolean enableAutomessage = false;
    private String version = "";



    private UserData() {
    }



    public String getTopLogin() {
        return this.topLogin.isEmpty() ? getResources().getString(R.string.topLogin) : this.topLogin;
    }

    public String getRealTopLogin() {
        return this.topLogin;
    }

    public void setTopLogin(String topLogin) {
        this.topLogin = topLogin;
    }


    public String getTopPassword() {
        return this.topPassword.isEmpty() ? getResources().getString(R.string.topPassword) : this.topPassword;
    }

    public String getRealTopPassword() {
        return this.topPassword;
    }

    public void setTopPassword(String topPassword) {
        this.topPassword = topPassword;
    }


    public String getVersion() {
        return this.version.isEmpty() ? getResources().getString(R.string.version) : this.version;
    }

    public String getRealVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getOnlineLogin() {
        return this.onlineLogin.isEmpty() ? getResources().getString(R.string.onlineLogin) : this.onlineLogin;
    }

    public String getRealOnlineLogin() {
        return this.onlineLogin;
    }

    public void setOnlineLogin(String onlineLogin) {
        this.onlineLogin = onlineLogin;
    }


    public String getOnlinePassword() {
        return this.onlinePassword.isEmpty() ? getResources().getString(R.string.onlinePassword) : this.onlinePassword;
    }

    public String getRealOnlinePassword() {
        return this.onlinePassword;
    }

    public void setOnlinePassword(String onlinePassword) {
        this.onlinePassword = onlinePassword;
    }


    public String getAdminLogin() {
        return this.adminLogin.isEmpty() ? getResources().getString(R.string.adminLogin) : this.adminLogin;
    }

    public String getRealAdminLogin() {
        return this.adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }


    public String getAdminPassword() {
        return this.adminPassword.isEmpty() ? getResources().getString(R.string.adminPassword) : this.adminPassword;
    }

    public String getRealAdminPassword() {
        return this.adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }


    public String getAutomessage() {
        return this.automessage.isEmpty() ? getResources().getString(R.string.automessage) : this.automessage;
    }

    public String getRealAutomessage() {
        return this.automessage;
    }

    public void setAutomessage(String automessage) {
        this.automessage = automessage;
    }


    public boolean isRealEnableAutomessage() {
        return enableAutomessage;
    }

    public void setEnableAutomessage(boolean enableAutomessage) {
        this.enableAutomessage = enableAutomessage;
    }



    private static final UserData INSTANCE = new UserData();

    public static UserData getInstance() {
        return INSTANCE;
    }

}

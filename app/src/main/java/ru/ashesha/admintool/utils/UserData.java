package ru.ashesha.admintool.utils;

import ru.ashesha.admintool.R;

public class UserData {

    private String topLogin = "", topPassword = "";
    private String onlineLogin = "", onlinePassword = "";
    private String adminLogin = "", adminPassword = "";
    private String automessage = "";
    private boolean enableAutomessage = false, enableSoundClick = true;
    private String version = "";
    private final Device device;



    private UserData() {
        device = Device.getInstance();
    }



    public String getTopLogin() {
        return this.topLogin.isEmpty() ? device.getResources().getString(R.string.topLogin) : this.topLogin;
    }

    public String getRealTopLogin() {
        return this.topLogin;
    }

    public void setTopLogin(String topLogin) {
        this.topLogin = topLogin;
    }


    public String getTopPassword() {
        return this.topPassword.isEmpty() ? device.getResources().getString(R.string.topPassword) : this.topPassword;
    }
    public String getRealTopPassword() {
        return this.topPassword;
    }
    public void setTopPassword(String topPassword) {
        this.topPassword = topPassword;
    }


    public String getVersion() {
        return this.version.isEmpty() ? device.getResources().getString(R.string.version) : this.version;
    }
    public String getRealVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }


    public String getOnlineLogin() {
        return this.onlineLogin.isEmpty() ? device.getResources().getString(R.string.onlineLogin) : this.onlineLogin;
    }
    public String getRealOnlineLogin() {
        return this.onlineLogin;
    }
    public void setOnlineLogin(String onlineLogin) {
        this.onlineLogin = onlineLogin;
    }


    public String getOnlinePassword() {
        return this.onlinePassword.isEmpty() ? device.getResources().getString(R.string.onlinePassword) : this.onlinePassword;
    }
    public String getRealOnlinePassword() {
        return this.onlinePassword;
    }
    public void setOnlinePassword(String onlinePassword) {
        this.onlinePassword = onlinePassword;
    }


    public String getAdminLogin() {
        return this.adminLogin.isEmpty() ? device.getResources().getString(R.string.adminLogin) : this.adminLogin;
    }
    public String getRealAdminLogin() {
        return this.adminLogin;
    }
    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }


    public String getAdminPassword() {
        return this.adminPassword.isEmpty() ? device.getResources().getString(R.string.adminPassword) : this.adminPassword;
    }
    public String getRealAdminPassword() {
        return this.adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }


    public String getAutomessage() {
        return this.automessage.isEmpty() ? device.getResources().getString(R.string.automessage) : this.automessage;
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

    public boolean isRealEnableSoundClick() {
        return enableSoundClick;
    }
    public void setEnableSoundClick(boolean enableSoundClick) {
        this.enableSoundClick = enableSoundClick;
    }



    private static UserData INSTANCE = null;

    public static UserData getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserData();
        return INSTANCE;
    }

}

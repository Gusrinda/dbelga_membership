package com.dbelgamembership.membersip.Screen.SetupOTP.Model;

public class PostBodyMessage {

    private String phone;
    private String message;
    private String spintax;

    public PostBodyMessage() {
    }

    public PostBodyMessage(String phone, String message, String spintax) {
        this.phone = phone;
        this.message = message;
        this.spintax = spintax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSpintax() {
        return spintax;
    }

    public void setSpintax(String spintax) {
        this.spintax = spintax;
    }
}

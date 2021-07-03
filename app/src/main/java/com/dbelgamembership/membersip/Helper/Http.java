package com.dbelgamembership.membersip.Helper;

public class Http {
    public static String server = "http://13.229.51.227/api/";
    public static String string(String string){
        return string.toString().replace(" ", "%20");
    }
}
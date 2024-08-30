package com.dbelgamembership.membersip.Screen.Log.model;

import com.google.type.DateTime;

import java.util.Date;

public class LogModel {

    private String typeLog;
    private Date dateLog;
    private String textLog;

    public LogModel(String typeLog, Date dateLog, String textLog) {
        this.typeLog = typeLog;
        this.dateLog = dateLog;
        this.textLog = textLog;
    }

    public Date getDateLog() {
        return dateLog;
    }

    public void setDateLog(Date dateLog) {
        this.dateLog = dateLog;
    }

    public LogModel() {
    }

    public String getTypeLog() {
        return typeLog;
    }

    public void setTypeLog(String typeLog) {
        this.typeLog = typeLog;
    }


    public String getTextLog() {
        return textLog;
    }

    public void setTextLog(String textLog) {
        this.textLog = textLog;
    }
}

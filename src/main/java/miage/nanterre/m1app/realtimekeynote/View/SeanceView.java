package miage.nanterre.m1app.realtimekeynote.View;

import java.util.Date;

public class SeanceView {

    private String file;

    private String name;

    private String subject;

    private String room;

    private String publiq;

    private Date date;

    private Date beginningTime;

    private Date endingTime;

    private String description;

    private String user;

    private int participants;

    public SeanceView(String name, String subject, String room, String publiq, Date date, Date beginningTime, Date endingTime, String description, String user, int participants, String file) {
        this.name = name;
        this.subject = subject;
        this.room = room;
        this.publiq = publiq;
        this.date = date;
        this.beginningTime = beginningTime;
        this.endingTime = endingTime;
        this.description = description;
        this.user = user;
        this.participants = participants;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getRoom() {
        return room;
    }

    public String getPubliq() {
        return publiq;
    }

    public Date getDate() {
        return date;
    }

    public Date getBeginningTime() {
        return beginningTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public int getParticipants() {
        return participants;
    }

    public String getFile() {
        return file;
    }
}
package miage.nanterre.m1app.realtimekeynote.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Seance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @Column(length = 60, nullable = true)
    private String name;

    @Column(length = 60, nullable = true)
    private String subject;

    @Column(length = 60, nullable = true)
    private String room;

    @Column(name = "public", length = 60, nullable = false)
    private String publiq;

    @Temporal(TemporalType.DATE)
    @Column(name="date", length = 60, nullable = true)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(name="beginning_time", length = 60, nullable = true)
    private Date beginningTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "ending_time",length = 60, nullable = true)
    private Date endingTime;

    @Column(length = 60, nullable = true)
    private String description;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false,
            mappedBy = "seance")
    private SeanceAnalytics seanceAnalytics;

    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false,
            mappedBy = "seance")
    private VideoProcessState videoProcessState;

    @JsonBackReference
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="user")
    private User user;

    @Column(nullable = false)
    private int participants;

    public Seance() {
        seanceAnalytics = new SeanceAnalytics(this, null, 0,0);
        videoProcessState = new VideoProcessState(this);
    }

    public Seance(String subject, String room, String publiq, Date date, Date beginningTime, String description, User user, int participants) {
        this.subject = subject;
        this.room = room;
        this.publiq = publiq;
        this.date = date;
        this.beginningTime = beginningTime;
        this.description = description;
        this.user = user;
        this.participants = participants;

        seanceAnalytics = new SeanceAnalytics(this, null,0,0);
        videoProcessState = new VideoProcessState(this);
    }

    public long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public Seance setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getRoom() {
        return room;
    }

    public Seance setRoom(String room) {
        this.room = room;
        return this;
    }

    public String getPubliq() {
        return publiq;
    }

    public Seance setPubliq(String publiq) {
        this.publiq = publiq;
        return this;
    }

    public Seance setDate(Date date) {
        this.date = date;
        return this;
    }

    public VideoProcessState getVideoProcessState() {
        return videoProcessState;
    }

    public void setVideoProcessState(VideoProcessState videoProcessState) {
        this.videoProcessState = videoProcessState;
    }

    public Date getDate(){
        return date;
    }

    public Date getBeginningTime() {
        return beginningTime;
    }

    public Seance setBeginningTime(Date beginningTime) {
        this.beginningTime = beginningTime;
        return this;
    }

    public String getDescription() {
        return description;
    }
    public Seance setDescription(String description) {
        this.description = description;
        return this;
    }
    public SeanceAnalytics getSeanceAnalytics() {
        return seanceAnalytics;
    }
    public Seance setSeanceAnalytics(SeanceAnalytics seanceAnalytics) {
        this.seanceAnalytics = seanceAnalytics;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Seance setUser(User user) {
        this.user = user;
        return this;
    }

    public int getParticipants() {
        return participants;
    }

    public Seance setParticipants(int participants) {
        this.participants = participants;
        return this;
    }

    public String getName() {
        return name;
    }

    public Seance setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", room='" + room + '\'' +
                ", publiq='" + publiq + '\'' +
                ", date=" + date +
                ", beginningTime=" + beginningTime +
                ", endingTime=" + endingTime +
                ", description='" + description + '\'' +
                ", seanceAnalytics=" + seanceAnalytics +
                ", user=" + user +
                ", participants=" + participants +
                '}';
    }
}

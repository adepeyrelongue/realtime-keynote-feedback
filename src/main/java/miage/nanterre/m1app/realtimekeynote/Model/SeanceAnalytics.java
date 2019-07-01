package miage.nanterre.m1app.realtimekeynote.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class SeanceAnalytics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Seance seance;

    @Column(nullable = true, length=260000,unique = false)
    private String analyticsData;

    @Column(nullable = true)
    private long duration;

    @Column(nullable = true)
    private long maxParticipantsObserved;


    public SeanceAnalytics() {
    }

    public SeanceAnalytics(Seance seance, String analyticsData, long duration, long maxParticipantsObserved) {
        this.seance = seance;
        this.analyticsData = analyticsData;
        this.duration = duration;
        this.maxParticipantsObserved = maxParticipantsObserved;
    }

    public long getMaxParticipantsObserved() {
        return maxParticipantsObserved;
    }

    public void setMaxParticipantsObserved(long maxParticipantsObserved) {
        this.maxParticipantsObserved = maxParticipantsObserved;
    }
    public long getDuration(){
        return this.duration;
    }

    public void setDuration(long duration){
        this.duration = duration;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public String getAnalyticsData() {
        return analyticsData;
    }

    public void setAnalyticsData(String analyticsData) {
        this.analyticsData = analyticsData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeanceAnalytics that = (SeanceAnalytics) o;
        return id == that.id &&
                Objects.equals(seance, that.seance) &&
                analyticsData.equals(that.analyticsData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, seance);
        result = 31 * result ;
        return result;
    }
}
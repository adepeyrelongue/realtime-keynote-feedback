package miage.nanterre.m1app.realtimekeynote.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
 public class VideoProcessState implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Seance seance;

    @Column(nullable = false,unique = false)
    private Boolean active;

    public VideoProcessState(){

    }

    public VideoProcessState(Seance seance) {
        this.seance = seance;
        this.active = false;
    }

    public long getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}

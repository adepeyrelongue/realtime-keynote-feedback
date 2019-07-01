package miage.nanterre.m1app.realtimekeynote.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(updatable = false, nullable = false, unique = true)
    private long id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(length = 60, nullable = false)
    private String lastname;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, targetEntity=Seance.class, mappedBy = "user")
    private List<Seance> seance;

    public User() {
    }

    public User(String name, String lastname, List<Seance> seance) {
        this.name = name;
        this.lastname = lastname;
        this.seance = seance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSeances(List<Seance> seance) {
        this.seance = seance;
    }

    @JsonIgnore
    public List<Seance> getSeances() {
        return seance;
    }

    @Override
    public String
    toString() {
        return "";
        /*"User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", seance=" + seance +
                '}';*/
    }
}
package miage.nanterre.m1app.realtimekeynote.Builder;

import miage.nanterre.m1app.realtimekeynote.Interface.SeanceBuilderInterface;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;

import java.util.Date;

public class SeanceBuilder implements SeanceBuilderInterface {
    private Seance seance;

    public SeanceBuilder() {
        this.seance = new Seance();
    }

    public SeanceBuilder setName(String name) {
        seance.setName(name);
        return this;
    }

    public SeanceBuilder setSubject(String subject) {
        seance.setSubject(subject);
        return this;
    }

    public SeanceBuilder setRoom(String salle) {
        seance.setRoom(salle);
        return this;
    }

    public SeanceBuilder setPubliq(String publique) {
        seance.setPubliq(publique);
        return this;
    }

    public SeanceBuilder setBeginningDate(Date date) {
        seance.setBeginningTime(date);
        return this;
    }


    public SeanceBuilder setDescription(String description) {
        seance.setDescription(description);
        return this;
    }

    public SeanceBuilder setUser(User user) {
        seance.setUser(user);
        return this;
    }

    public SeanceBuilder setParticipants(int participants) {
        seance.setParticipants(participants);
        return this;
    }

    public Seance build() {
        return seance;
    }
}

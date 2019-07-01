package miage.nanterre.m1app.realtimekeynote.Interface;

import miage.nanterre.m1app.realtimekeynote.Builder.SeanceBuilder;
import miage.nanterre.m1app.realtimekeynote.Model.User;

import java.util.Date;

public interface SeanceBuilderInterface {

    public SeanceBuilder setName(String name);
    public SeanceBuilder setSubject(String subject);
    public SeanceBuilder setRoom(String salle);
    public SeanceBuilder setPubliq(String publique);
    public SeanceBuilder setBeginningDate(Date date);
    public SeanceBuilder setDescription(String Description);
    public SeanceBuilder setUser(User user);
    public SeanceBuilder setParticipants(int participants);

}

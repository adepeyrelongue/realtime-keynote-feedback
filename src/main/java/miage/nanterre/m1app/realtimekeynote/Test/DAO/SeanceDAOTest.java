package miage.nanterre.m1app.realtimekeynote.Test.DAO;

import miage.nanterre.m1app.realtimekeynote.DAO.SeanceDAO;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;

public class SeanceDAOTest {
    SeanceRepository seanceRepository;
    SeanceDAO seanceDAO;
    UserRepository userRepository;
    SeanceAnalyticsRepository seanceAnalyticsRepository;
    User user;


    @BeforeClass
    public void setUp() throws Exception {
        seanceDAO = new SeanceDAO(seanceRepository);
    }

    @Test
    public void create(){
        /**user = new User("Christelle","Ilunga",null);

        userRepository.save(user);

        Seance seance = new Seance(
                "Test",
                "2",
                "Public",
                null,
                null,
                null,
                user,
                50);

        seanceRepository.save(seance);

        seanceAnalyticsRepository.save(new SeanceAnalytics(seance, null));

        userRepository.findById((long)1).get();*/
    }

    @Test
    public void testDeleteSeance(){
        seanceDAO.deleteSeance(user.getId());
    }


}

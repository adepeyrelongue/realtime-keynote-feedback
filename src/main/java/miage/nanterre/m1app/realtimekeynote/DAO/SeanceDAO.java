package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;

@Service
@RequestMapping("/seance")
public class SeanceDAO {

    @Autowired
    private SeanceRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeanceAnalyticsRepository seanceAnalyticsRepository;

    public SeanceDAO(SeanceRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/list")
    public Iterable<Seance> getAllSeance() {
        return repository.findAll();
    }

    @RequestMapping("/get/sceanceId")
    public Seance getSeance(@RequestParam("seanceId") long seanceId) {
        return repository.findById(seanceId).get();
    }

    @RequestMapping("/get/delete")
    public void deleteSeance(@RequestParam("seanceId") long seanceId) {
        repository.deleteById(seanceId);
    }

    @PostConstruct
    public void testCreateSeance() {
      User user = new User("Christelle", "Ilunga", null);
        userRepository.save(user);
        Seance seance1 = new Seance(
                "Conférence espace des Science 1",
                "Amphi G",
                "M1 Stats",
                DateHelper.asDate(LocalDate.of(2018, 12, 20)),
                new Date(2018,1,13, 13, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance1.getSeanceAnalytics().setDuration(6000);
        seance1.getSeanceAnalytics().setMaxParticipantsObserved(42);
        seance1.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance1));
        repository.save(seance1);


        Seance seance2 = new Seance(
                "Conférence espace des Science 2",
                "Amphi G",
                "M1 Stats",
                DateHelper.asDate(LocalDate.of(2019, 1, 28)),
                new Date(2018,1,13, 13, 30),

                "Description d'une conférence scientifique.",
                user,
                50);
        seance2.getSeanceAnalytics().setDuration(10000);
        seance2.getSeanceAnalytics().setMaxParticipantsObserved(43);
        seance2.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance2));
        repository.save(seance2);


        Seance seance3  = new Seance(
                "Conférence espace des Science 1",
                "Amphi G",
                "M1 Stats",
                DateHelper.asDate(LocalDate.of(2019, 2, 28)),
                new Date(2018,1,13, 18, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance3.getSeanceAnalytics().setDuration(10000);
        seance3.getSeanceAnalytics().setMaxParticipantsObserved(45);
        seance3.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance3));
        repository.save(seance3);


        Seance seance4 = new Seance(
                "Conférence BIG DATA",
                "Amphi G",
                "M1 Stats",
                DateHelper.asDate(LocalDate.of(2019, 3, 28)),
                new Date(2018,1,13, 10, 12),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance4.getSeanceAnalytics().setDuration(6000);
        seance4.getSeanceAnalytics().setMaxParticipantsObserved(48);
        seance4.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance4));
        repository.save(seance4);

        String analytics5 = "45,45,45,45,45,45";
        Seance seance5 = new Seance(
                "Conférence POO",
                "Amphi G",
                "L3 MIAGE",
                DateHelper.asDate(LocalDate.of(2019, 4, 28)),
                new Date(2018,1,13, 11, 30),
                "Description d'une conférence scientifique.",
                user,
                52);
        seance5.getSeanceAnalytics().setDuration(5000);
        seance5.getSeanceAnalytics().setMaxParticipantsObserved(34);
        seance5.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance5));
        repository.save(seance5);

        Seance seance6 = new Seance(
                "Conférence espace des Science 1",
                "Amphi G",
                "M1 MIAGE",
                DateHelper.asDate(LocalDate.of(2019, 5, 28)),
                new Date(2018,1,13, 13, 30),
                "Description d'une conférence scientifique.",
                user,
                50);
        seance6.getSeanceAnalytics().setDuration(500);
        seance6.getSeanceAnalytics().setMaxParticipantsObserved(48);
        seance6.getSeanceAnalytics().setAnalyticsData(Helper.getRandomData(seance6));
        repository.save(seance6);
        User user1 = userRepository.findById((long) 1).get();
    }
}

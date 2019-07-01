package miage.nanterre.m1app.realtimekeynote.Controller;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import miage.nanterre.m1app.realtimekeynote.Builder.SeanceBuilder;
import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Exception.UserNotFoundException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Model.VideoProcessState;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.VideoProcessStateRepository;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceAnalyticsService;
import miage.nanterre.m1app.realtimekeynote.Service.SeanceService;
import miage.nanterre.m1app.realtimekeynote.Service.VideoProcessService;
import miage.nanterre.m1app.realtimekeynote.View.SeanceView;
import miage.nanterre.m1app.realtimekeynote.helpers.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.ID;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.SUBJECT;

@Controller
@RequestMapping(value = "/seance")
public class SeanceController extends SeanceBuilder {

    private UserRepository userRepository;
    private SeanceRepository seanceRepository;
    private SeanceAnalyticsRepository analyticsRepo;
    private VideoProcessStateRepository videoProcessStateRepo;

    private static final boolean ENABLE_VIDEO_ANALYSIS = true;

    public SeanceController(UserRepository userRepository, SeanceRepository seanceRepository, SeanceAnalyticsRepository analyticsRepo, VideoProcessStateRepository videoProcessStateRepo) {
        this.userRepository = userRepository;
        this.seanceRepository = seanceRepository;
        this.analyticsRepo = analyticsRepo;
        this.videoProcessStateRepo = videoProcessStateRepo;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/all/user/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSeanceByUser(@PathVariable("id") long userId) {
        User user = userRepository.findById(userId).get();
        return new ResponseEntity<Object>(user.getSeances(), HttpStatus.OK);
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/test", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> sendAnalyticsData(@RequestParam("id") long id) throws AnalyticsException {
        VideoProcessService videoService = new VideoProcessService(seanceRepository, analyticsRepo, videoProcessStateRepo);
        videoService.test(seanceRepository.findById(id).get());
        return new ResponseEntity<Object>("ok", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getAllSeanceData() {
        ArrayList<HashMap> response = SeanceService.getAllSeances(seanceRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/get/seances-in-process", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Object> getSessionsInProcess() {
        VideoProcessService service = new VideoProcessService(seanceRepository, analyticsRepo, videoProcessStateRepo);
        return new ResponseEntity<>(service.getSessionsInProcess(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/create")
    public ResponseEntity<Object> createSeance(@RequestBody SeanceView seanceView) throws UserNotFoundException {
        User user = userRepository
                .findById((long) 1)
                .orElseThrow(UserNotFoundException::new);
        String fileToAnalyse = seanceView.getFile();
        Seance seance = new Seance();
        seance
                .setName(seanceView.getName())
                .setSubject(seanceView.getSubject())
                .setDescription(seanceView.getDescription())
                .setPubliq(seanceView.getPubliq())
                .setParticipants(seanceView.getParticipants())
                .setDate(seanceView.getDate())
                .setBeginningTime(seanceView.getBeginningTime())
                .setRoom(seanceView.getRoom())
                .setUser(user);

        SeanceAnalytics analytics = seance.getSeanceAnalytics();
        seanceRepository.save(seance);
        VideoProcessState state = seance.getVideoProcessState();
        state.setActive(true);
        videoProcessStateRepo.save(state);
        VideoProcessService videoService = new VideoProcessService(seanceRepository, analyticsRepo, videoProcessStateRepo);

        videoService.analyse(fileToAnalyse,seance);
        HashMap<String,Object> hash = new HashMap<>();
        hash.put(String.valueOf(ID), seance.getId());
        hash.put(String.valueOf(SUBJECT), seance.getSubject());
        return new ResponseEntity<>(hash, HttpStatus.OK);
    }
}

package miage.nanterre.m1app.realtimekeynote.VideoProcessing;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Model.VideoProcessState;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.VideoProcessStateRepository;
import miage.nanterre.m1app.realtimekeynote.Service.UploadService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Scope("prototype")
public class AnalysisThread extends Thread {
    private String videoName;
    private Seance seance;
    private VideoProcessStateRepository videoProcessStateRepository;
    private SeanceAnalyticsRepository seanceAnalyticsRepository;
    public void setVideoName(String videoName){
        this.videoName = UploadService.getCleanFileName(videoName);
    }
    public void setSeance(Seance seance){
        this.seance = seance;
    }
    public void setVideoProcessStateRepository(VideoProcessStateRepository videoProcessStateRepository) {
        this.videoProcessStateRepository = videoProcessStateRepository;
    }
    public void setSeanceAnalyticsRepository(SeanceAnalyticsRepository seanceAnalyticsRepository){
        this.seanceAnalyticsRepository = seanceAnalyticsRepository;
    }
    @Override
    public void run(){
        VideoProcessState videoProcessState = seance.getVideoProcessState();
        nu.pattern.OpenCV.loadShared();
        String path = UploadService.uploadDir + videoName;
        String chemin = path.replace("~","\\");
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        //Create new MAT object
        Mat frame = new Mat();
        //Create new VideoCapture object
        VideoCapture camera = new VideoCapture(path);
        int secondRatio = (int) camera.get(Videoio.CAP_PROP_FPS);
        System.out.println(secondRatio);
        String xmlFile = "XML\\lbpcascade_frontalface.xml";
        MatOfRect faceDetection = new MatOfRect();
        CascadeClassifier cc = new CascadeClassifier(xmlFile);
        int fPos = 0;
        String strAnalytics = "";
        long total  = 0;
        long max = 1;
        System.out.println("Début de l'analyse vidéo...");
        while (true) {
            if (camera.read(frame)) {
                if(fPos % secondRatio == 0) {
                    cc.detectMultiScale(frame, faceDetection);
                    total = faceDetection.total();
                    strAnalytics+=faceDetection.total()+",";
                    System.out.println(fPos);
                    if(total > max){
                        max = total;
                    }
                }
                fPos++;
            } else {
                break;
            }
        }
        System.out.println("Analyse vidéo terminée");
        videoProcessState.setActive(false);
        videoProcessStateRepository.save(videoProcessState);
        SeanceAnalytics seanceAnalytics = seance.getSeanceAnalytics();
        seanceAnalytics.setMaxParticipantsObserved(max);

        if(strAnalytics.length()>1)
            seanceAnalytics.setAnalyticsData(strAnalytics.substring(0, strAnalytics.length() - 1));
        else
            seanceAnalytics.setAnalyticsData("");

        long duration = fPos/secondRatio;
        duration = duration > 1 ? duration : 1;
        seanceAnalytics.setDuration(duration);

        seanceAnalyticsRepository.save(seanceAnalytics);
        File file = new File(path);
        file.delete();
    }
}




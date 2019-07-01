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
import java.util.ArrayList;

@Component
@Scope("prototype")
public class AnalysisThreadTest extends Thread {
    private String videoName;
    private Seance seance;
    private VideoProcessStateRepository videoProcessStateRepository;
    private SeanceAnalyticsRepository seanceAnalyticsRepository;
    public void setVideoName(String videoName){
        this.videoName = videoName;
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
        System.out.println("Start video processing.");
        VideoProcessState videoProcessState = seance.getVideoProcessState();
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally();
        String path = UploadService.uploadDir + videoName;
        String chemin = path.replace("~","\\");
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        //Create new MAT object
        Mat frame = new Mat();
        //Create new VideoCapture object
        VideoCapture camera = new VideoCapture("http://techslides.com/demos/sample-videos/small.mp4");
        int secondRatio = (int) camera.get(Videoio.CAP_PROP_FPS);
        System.out.println(secondRatio);
        String xmlFile = "XML\\lbpcascade_frontalface.xml";
        MatOfRect faceDetection = new MatOfRect();
        CascadeClassifier cc = new CascadeClassifier(xmlFile);
        int fPos = 0;
        String strAnalytics = "";
        while (true) {
            if (camera.read(frame)) {
                if(fPos % secondRatio == 0) {
                    cc.detectMultiScale(frame, faceDetection);
                    strAnalytics+=faceDetection.total()+",";
                    System.out.println(fPos);
                }
                fPos++;
            } else {
                break;
            }
        }
        System.out.println("Video process finished.");
        videoProcessState.setActive(false);
        videoProcessStateRepository.save(videoProcessState);
        SeanceAnalytics seanceAnalytics = seance.getSeanceAnalytics();
        if(strAnalytics.length()>0)
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




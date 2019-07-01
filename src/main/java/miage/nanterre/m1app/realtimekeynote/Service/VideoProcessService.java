package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.VideoProcessState;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.VideoProcessStateRepository;
import miage.nanterre.m1app.realtimekeynote.VideoProcessing.AnalysisThread;
import miage.nanterre.m1app.realtimekeynote.VideoProcessing.AnalysisThreadTest;

import java.util.ArrayList;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.ID;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.SUBJECT;

public class VideoProcessService {
     private SeanceRepository seanceRepository;
     private SeanceAnalyticsRepository seanceAnalyticsRepository;
     private VideoProcessStateRepository videoProcessStateRepository;

    public VideoProcessService( SeanceRepository seanceRepository,SeanceAnalyticsRepository seanceAnalyticsRepository, VideoProcessStateRepository videoProcessStateRepository) {
        this.seanceRepository = seanceRepository;
        this.seanceAnalyticsRepository = seanceAnalyticsRepository;
        this.videoProcessStateRepository = videoProcessStateRepository;
    }

    public void analyse(String nom,Seance seance) {

        AnalysisThread videoProcess = new AnalysisThread();
        videoProcess.setVideoName(nom);
        videoProcess.setSeanceAnalyticsRepository(seanceAnalyticsRepository);
        videoProcess.setSeance(seance);
        videoProcess.setVideoProcessStateRepository(videoProcessStateRepository);
        videoProcess.start();
    }

    public void test(Seance seance){
        AnalysisThreadTest videoProcess = new AnalysisThreadTest();
        videoProcess.setSeanceAnalyticsRepository(seanceAnalyticsRepository);
        videoProcess.setSeance(seance);
        videoProcess.setVideoProcessStateRepository(videoProcessStateRepository);
        videoProcess.start();
    }



    public ArrayList<HashMap> getSessionsInProcess(){
        ArrayList<HashMap> list = new ArrayList<>();
        ArrayList<VideoProcessState> states = (ArrayList<VideoProcessState>) videoProcessStateRepository.findAll();
        for(VideoProcessState state : states){
            if(state.getActive()){
                Seance seance = state.getSeance();
                HashMap<String, Object> hash = new HashMap<>();
                hash.put(String.valueOf(ID), seance.getId());
                hash.put(String.valueOf(SUBJECT), seance.getSubject());
                list.add(hash);
            }
        }
        HashMap<String, Object> hash = new HashMap<>();
        return list;
    }


}

package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;

import java.util.ArrayList;
import java.util.HashMap;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.*;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceAnalyticsEnum.*;

public class SeanceService {

    public static ArrayList<HashMap> getAllSeances(SeanceRepository seanceRepository) {

        ArrayList<HashMap> response = new ArrayList<>();
        Iterable<Seance> seances = seanceRepository.findAll();

        for (Seance seance : seances)
            if (seance != null && seance.getVideoProcessState().getActive()==false) {
                HashMap<String, Object> seanceData = new HashMap<String, Object>();
                seanceData.put(String.valueOf(SUBJECT), seance.getSubject());
                seanceData.put(String.valueOf(PUBLIC), seance.getPubliq());
                seanceData.put(String.valueOf(ROOM), seance.getRoom());
                seanceData.put(String.valueOf(PARTICIPANTS), seance.getParticipants());
                seanceData.put(String.valueOf(DATE), seance.getDate());
                seanceData.put(String.valueOf(BEGINNING_TIME), seance.getBeginningTime());
                seanceData.put(String.valueOf(ID), seance.getId());
                seanceData.put(String.valueOf(ATTENTION_AVG),
                        SeanceAnalyticsService.getAverageSessionAttention(
                                seance.getSeanceAnalytics().getMaxParticipantsObserved(),
                                SeanceAnalyticsService
                                        .parseAnalytics(
                                        seance.getSeanceAnalytics()
                                        .getAnalyticsData())));
                seanceData.put(String.valueOf(DURATION), CalcDuration(seance));
                response.add(seanceData);
            }
        return response;
    }

    public static String CalcDuration(Seance seance) {
        long duration = seance.getSeanceAnalytics().getDuration();
        int hours = Math.toIntExact(duration / 60 / 60);
        int min = Math.toIntExact((duration / 60) - hours * 60);
        min = min > 0 ? min : 1;
        return hours + ":" + min;
    }

}

package miage.nanterre.m1app.realtimekeynote.Service;

import miage.nanterre.m1app.realtimekeynote.Exception.AnalyticsException;
import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceAnalyticsRepository;
import miage.nanterre.m1app.realtimekeynote.Repository.SeanceRepository;
import miage.nanterre.m1app.realtimekeynote.helpers.DateHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceAnalyticsEnum.*;
import static miage.nanterre.m1app.realtimekeynote.Enum.SeanceEnum.*;

public class SeanceAnalyticsService {
    public final static int MAX_ATTENTION = 50;
    public final static String STATISTICS_SEPARATOR = ",";

    private SeanceAnalyticsRepository analyticsRepository;
    private SeanceRepository seanceRepository;

    public SeanceAnalyticsService(SeanceAnalyticsRepository analyticsRepository, SeanceRepository seanceRepository) {
        this.analyticsRepository = analyticsRepository;
        this.seanceRepository = seanceRepository;
    }


    public static HashMap<String, Object> getDashboardStatistics(SeanceAnalyticsRepository analyticsRepository)
            throws AnalyticsException {

        HashMap<String, Object> response = new HashMap<String, Object>();
        ArrayList<LocalDate> months = getSixLastMonthsLabels();
        ArrayList<SeanceAnalytics> analytics = (ArrayList<SeanceAnalytics>) analyticsRepository.findAll();
        HashMap<LocalDate, ArrayList> sessionsPerMonths = getSessionsPerMonths(analytics, months);
        ArrayList<Double> AvgPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AttentionPerMonthList = new ArrayList<Double>();
        ArrayList<Double> AbsentPerMonthList = new ArrayList<Double>();

        for (LocalDate date : months) {
            ArrayList sessions = sessionsPerMonths.get(date);
            AvgPerMonthList.add(getAttentionAveragePerMonth(sessions));
            AttentionPerMonthList.add(getAttentionDiffPerMonth(sessions));
            AbsentPerMonthList.add(getAbsentAveragePerMonth(sessions));
        }

        response.put(String.valueOf(MONTHS), months);
        response.put(String.valueOf(ATTENTION_AVG_PER_MONTH), AvgPerMonthList);
        response.put(String.valueOf(ATTENTION_DIFF_PER_MONTH), AttentionPerMonthList);
        response.put(String.valueOf(ABSENT_AVG_PER_MONTH), AbsentPerMonthList);
        System.out.println(AvgPerMonthList);
        response.put(String.valueOf(ATTENTION_AVG), AvgPerMonthList
                .stream()
                .filter(mean -> !Double.isNaN(mean))
                .reduce(0., (a, b) -> a + b)
                / AvgPerMonthList.size());

        return response;
    }

    public static HashMap<String, Object> getSessionStatistics(SeanceRepository seanceRepository, long sessionId)
            throws AnalyticsException {

        HashMap<String, Object> response = new HashMap<String, Object>();
        Seance seance = seanceRepository.findById(sessionId).get();

        if (seance.getSeanceAnalytics().getAnalyticsData() != null) {
            HashMap<String, Object> seanceData = new HashMap<String, Object>();
            seanceData.put(String.valueOf(SUBJECT), seance.getSubject());
            seanceData.put(String.valueOf(PUBLIC), seance.getPubliq());
            seanceData.put(String.valueOf(ROOM), seance.getRoom());
            seanceData.put(String.valueOf(PARTICIPANTS), seance.getParticipants());
            seanceData.put(String.valueOf(OBSERVED_PARTICIPANTS), seance.getSeanceAnalytics().getMaxParticipantsObserved());
            long absents = seance.getParticipants() - seance.getSeanceAnalytics().getMaxParticipantsObserved();
            absents = absents > 0 ? absents : 0;
            seanceData.put(String.valueOf(ABSENT_PARTICIPANTS), absents);
            seanceData.put(String.valueOf(DATE), seance.getDate());
            seanceData.put(String.valueOf(BEGINNING_TIME), seance.getBeginningTime());
            seanceData.put(String.valueOf(DURATION), SeanceService.CalcDuration(seance));

            long participants = seance.getSeanceAnalytics().getMaxParticipantsObserved();
            ArrayList<Integer> parsedAnalytics = parseAnalytics(seance.getSeanceAnalytics().getAnalyticsData());

            response.put(String.valueOf(ATTENTION_AVG), getAverageSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(ATTENTION_MAX), getBestSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(ATTENTION_MIN), getWorstSessionAttention(participants, parsedAnalytics));
            response.put(String.valueOf(SESSION), seanceData);
            response.put(String.valueOf(SESSION_ANALYTICS_DATA), parseAnalyticsResume(seance, parsedAnalytics));
        } else {
            throw new AnalyticsException("Aucune seance à analyser !");

        }

        return response;
    }

    private static double getAttentionAveragePerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {

        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getAverageSessionAttention(session.getSeance().getSeanceAnalytics().getMaxParticipantsObserved(),
                    parseAnalytics(session.getAnalyticsData())));
        }
        return collector
                .stream()
                .reduce(0., (a, b) -> a + b)
                / collector.size();
    }

    private static double getAttentionDiffPerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getBestSessionAttention(session.getSeance().getSeanceAnalytics().getMaxParticipantsObserved(),
                    parseAnalytics(session.getAnalyticsData()))
                    - getWorstSessionAttention(session.getSeance().getSeanceAnalytics().getMaxParticipantsObserved(),
                    parseAnalytics(session.getAnalyticsData())));
        }
        return (collector
                .stream()
                .reduce(0., (a, b) -> a + b) * 1.)
                / (collector.size() * 1.);
    }

    private static double getAbsentAveragePerMonth(ArrayList<SeanceAnalytics> sessions) throws AnalyticsException {
       /* if (sessions.isEmpty())
            throw new AnalyticsException("Aucune session à analyser !");*/

        ArrayList<Double> collector = new ArrayList<Double>();
        for (SeanceAnalytics session : sessions) {
            collector.add(getAverageAbsentParticipants(session.getSeance().getSeanceAnalytics().getMaxParticipantsObserved(),
                    parseAnalytics(session.getAnalyticsData()))
            );
        }
        return (collector
                .stream()
                .reduce(0., (a, b) -> a + b) * 1.)
                / (collector.size() * 1.);
    }

    public static double getAverageSessionAttention(long participants, ArrayList<Integer> dataAnalytics) {

        return (((dataAnalytics
                .stream()
                .reduce(0, (a, b) -> a + b) * 1.)
                / (dataAnalytics.size() * 1.))
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getBestSessionAttention(long participants, ArrayList<Integer> dataAnalytics)
           {

        return ((Collections.max(dataAnalytics) * 1.)
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getWorstSessionAttention(long participants, ArrayList<Integer> dataAnalytics)
             {

        return ((Collections.min(dataAnalytics) * 1.)
                / (participants * 1.))
                * MAX_ATTENTION;
    }

    private static double getAverageAbsentParticipants(long participants, ArrayList<Integer> dataAnalytics)
          {
        double data = participants * 1. -
                ((dataAnalytics.stream()
                        .reduce(0, (a, b) -> a + b) * 1.)
                        / (dataAnalytics.size() * 1.));
        return data;
    }

    private static HashMap<LocalDate, ArrayList> getSessionsPerMonths(ArrayList<SeanceAnalytics> analytics, ArrayList<LocalDate> months)
        {

        HashMap<LocalDate, ArrayList> datasets = new HashMap<LocalDate, ArrayList>();
        for (LocalDate date : months) {
            datasets.put(date, getSessionPerMonth(analytics, date));
        }
        return datasets;
    }

    private static ArrayList<LocalDate> getSixLastMonthsLabels() {
        LocalDate dateNow = LocalDate.now();
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        dates.add(dateNow);
        dates.add(dateNow.minusMonths(1));
        dates.add(dateNow.minusMonths(2));
        dates.add(dateNow.minusMonths(3));
        dates.add(dateNow.minusMonths(4));
        dates.add(dateNow.minusMonths(5));
        Collections.reverse(dates);
        return dates;
    }

    private static ArrayList getSessionPerMonth(ArrayList<SeanceAnalytics> analytics, LocalDate date) {
        ArrayList sessions = new ArrayList();
        for (SeanceAnalytics analytic : analytics) {
            LocalDate current = DateHelper.asLocalDate(analytic.getSeance().getDate());
            if (current.getMonthValue() == date.getMonthValue() &&
                    current.getYear() == date.getYear() && analytic.getSeance()
                    .getVideoProcessState().getActive() == false) {
                sessions.add(analytic);
            }
        }
        return sessions;
    }

    public static ArrayList<Integer> parseAnalytics(String analytics) {
        ArrayList<Integer> parsed = new ArrayList<Integer>();
        String[] parts = analytics.split(STATISTICS_SEPARATOR);
        for (String str : parts) {
            parsed.add(Integer.valueOf(str));
        }
        return parsed;
    }

    public static ArrayList<HashMap> parseAnalyticsResume(Seance seance, ArrayList<Integer> parsed) {
        ArrayList<HashMap> collector = new ArrayList<>();
        int count = 0;
        long durationSecond = seance.getSeanceAnalytics().getDuration();
        long durationMin = (long) Math.ceil(durationSecond / 60);
        long partsNumber = 0;
        long milliSecondsSeparation = 0;

        if (durationMin >= 15) {
            partsNumber = (long) Math.ceil(durationMin * 1. / 15 * 1.);
            milliSecondsSeparation = 60000 * 15;
        } else {
            partsNumber = durationMin > 0 ? durationMin : 1;
            milliSecondsSeparation = 60000;
        }

        partsNumber = partsNumber > 0 ? partsNumber : 1;
        long frameByParts = parsed.size() / partsNumber;
        ArrayList<Double> part;
        HashMap datas = new HashMap();
        datas.put(String.valueOf(DATA), (double) 0.);
        collector.add(datas);
        while (count < partsNumber) {
            part = new ArrayList<Double>();
            for (long i = count * frameByParts; i < ((count + 1) * frameByParts)
                    && i < parsed.size(); i++) {
                part.add(parsed.get((int) i) * 1.);
            }
            datas = new HashMap();
            double value = part.stream()
                    .reduce(0., (a, b) -> (a + b)) * 1.
                    / part.size()
                    / seance.getSeanceAnalytics().getMaxParticipantsObserved()
                    * MAX_ATTENTION;
            datas.put(String.valueOf(DATA), value);
            collector.add(datas);
            count++;
        }
        Date date = seance.getBeginningTime();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        for (int i = 0; i < collector.size(); i++) {
            collector.get(i).put(String.valueOf(LABEL), localDateFormat.format(date));
            date = new Date(date.getTime() + milliSecondsSeparation);
        }
        collector.get(collector.size() - 1).replace(
                String.valueOf(LABEL),
                localDateFormat.format(new Date(seance.
                        getBeginningTime().
                        getTime() + durationSecond * 1000)));
        return collector;
    }


}

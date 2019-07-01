package miage.nanterre.m1app.realtimekeynote.Repository;

import miage.nanterre.m1app.realtimekeynote.Model.SeanceAnalytics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceAnalyticsRepository  extends CrudRepository<SeanceAnalytics, Long> {
}

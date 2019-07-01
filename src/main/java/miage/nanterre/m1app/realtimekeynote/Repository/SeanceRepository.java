package miage.nanterre.m1app.realtimekeynote.Repository;

import miage.nanterre.m1app.realtimekeynote.Model.Seance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepository extends CrudRepository<Seance, Long> {

}

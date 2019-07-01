package miage.nanterre.m1app.realtimekeynote.Repository;

import miage.nanterre.m1app.realtimekeynote.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}

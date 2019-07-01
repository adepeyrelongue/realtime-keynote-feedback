package miage.nanterre.m1app.realtimekeynote.DAO;

import miage.nanterre.m1app.realtimekeynote.Model.User;
import miage.nanterre.m1app.realtimekeynote.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequestMapping("/user")
public class UserDAO {

    @Autowired
    private UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/list")
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @RequestMapping("/add")
    public void createUser(User user) {
        repository.save(user);
    }

    @RequestMapping("/delete/userId")
    public void deleteUser(@RequestParam("userId") long userId) {
        repository.deleteById(userId);
    }

    @RequestMapping("/update/userId")
    public User updateUser(@RequestParam("userId") long userId) {
        User user = repository.findById(userId).get();
        user.setName("blablabla");
        repository.save(user);
        return user;
    }
/*
    @PostConstruct
    public void testAddUsers(){
        User user = new User("Christelle","Ilunga",null);
        createUser(user);
    }

    @PostConstruct
    public void testGetAllUsers(){
        System.out.println("Users : "+getAllUsers());
    }

    @PostConstruct
    public void testDeleteUsers(){
        deleteUser(1);
    }

    @PostConstruct
    public void testUpdateUsers(){
        updateUser(2);
    }
*/
}

package miage.nanterre.m1app.realtimekeynote.Exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException() {
        System.out.println("User not found: Weird");
    }
}

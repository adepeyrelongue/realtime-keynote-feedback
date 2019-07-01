package miage.nanterre.m1app.realtimekeynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"miage.nanterre.m1app.realtimekeynote.Model", "miage.nanterre.m1app.realtimekeynote.DAO"})
public class RealtimeKeynoteApplication  {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeKeynoteApplication.class, args);
	}

}

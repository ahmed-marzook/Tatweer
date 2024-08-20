package com.al_najah.tatweer;

import com.al_najah.tatweer.dto.UserRecord;
import com.al_najah.tatweer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TatweerApplication implements CommandLineRunner {

  @Autowired private UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(TatweerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Hi");
    UserRecord userRecord = new UserRecord("marza004", "Ahmed", "Marzook", "ahmed@email.com", null);
    userService.addNewUser(userRecord);
  }
}

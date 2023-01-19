package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tqtk.Entity.SessionEntity;
import tqtk.Tqtk;
import tqtk.XuLy.login.LayThongTinSession;

@SpringBootApplication
@RestController
public class DemoApplication {
        public static int count = 0;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("count")
	public String getCountUsers(){
		count++;
		return "Number of users = "+ count;
	}
	
	@GetMapping("start")
	public String start(){
		if (LayThongTinSession.getListSession().size() < 1) {
            Thread t = new Thread() {
                public void run() {
                    tqtk.Tqtk.main();
                }
            };
            t.start();
        }
		return "ok";
	}

}

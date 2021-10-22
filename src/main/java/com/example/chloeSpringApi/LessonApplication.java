package com.example.chloeSpringApi;

import com.example.chloeSpringApi.models.Cat;
import com.example.chloeSpringApi.repos.CatRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

// ! This annotation gives you an ApplicationContext, which will make the
// ! dependency injection work for you. This means you don't have
// ! to create your own instances of classes like your controllers
// ! or your services.
@SpringBootApplication
public class LessonApplication {
	// ! Entrypoint of your spring boot app.
	public static void main(String[] args) {
		SpringApplication.run(LessonApplication.class, args);
	}

//	// ! This method will get run whenever our application starts.
//	// ! It will pre-fill the database with some development data.
//	@Bean
//	@Profile("!test") // ! Stop the commandLineRunner running when im testing.
//	CommandLineRunner commandLineRunner(CatRepo catRepo) {
//		return args -> {
//
//		};
//	}
}

package com.udyata.linentrack.linentrack;

import com.udyata.linentrack.linentrack.entity.role.Role;
import com.udyata.linentrack.linentrack.repository.role.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "LinenTrack Rest API's",
				description = "LinenTrack Rest API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Krishnan KV",
						email = "krishnan@udyata.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "LinenTrack API Documentation (Made with Spring boot)",
				url = "https://github.com/krishnan-cce/LinenTrack"
		),
		servers = {
				@Server(url = "https://linen-track.onrender.com", description = "Production server"),
				@Server(url = "http://localhost:5000", description = "Development server")
		}
)
@EnableScheduling
public class LinentrackApplication implements CommandLineRunner {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dubai"));
	}


	public static void main(String[] args) {
		SpringApplication.run(LinentrackApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer configurer(){
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry reg){
//				reg.addMapping("/**")
//						.allowedOriginPatterns("*")
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
//						.allowedHeaders("*")
//						.allowCredentials(true);
//			}
//		};
//	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

//		Role adminRole = new Role();
//		adminRole.setName("ADMIN");
//		roleRepository.save(adminRole);
//
//		Role userRole = new Role();
//		userRole.setName("USER");
//		roleRepository.save(userRole);

	}

}

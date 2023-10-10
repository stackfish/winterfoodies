package com.food.winterfoodies2;

import com.food.winterfoodies2.entity.Role;
import com.food.winterfoodies2.repository.CategoryRepository;
import com.food.winterfoodies2.repository.RoleRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
@EntityScan("com.food.winterfoodies2.entity")
@OpenAPIDefinition(
        info = @Info(
                title = "winterfoodies springmvc REST API Documentation",
                description = "Lento123 winterfoodies springmvc REST API Documentation",
                version ="v1",
                contact = @Contact(
                        name = "정원준",
                        email = "app8229@knou.ac.kr"),
                license = @License(
                        name = "Apache 2.0"
                )

        )
)
public class Winterfoodies2Application implements CommandLineRunner{

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Winterfoodies2Application.class, args);
    }

    private RoleRepository roleRepository;




    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);
    }
}

package com.aces.spring.login;

import com.aces.spring.login.models.ERole;
import com.aces.spring.login.models.Role;
import com.aces.spring.login.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringBootLoginExampleApplication {

	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootLoginExampleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void roleSeeding() {
		if(roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty())
		{
			for(ERole roleName : ERole.values()){
				if(roleRepository.findByName(roleName).isEmpty()){
					Role role = new Role(roleName);
					roleRepository.save(role);
				}
			}
		}
	}
}



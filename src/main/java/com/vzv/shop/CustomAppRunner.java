package com.vzv.shop;

import com.vzv.shop.entity.user.User;
import com.vzv.shop.enumerated.Role;
import com.vzv.shop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class CustomAppRunner implements ApplicationRunner {

	private final UserService userService;

	public CustomAppRunner(UserService userService) {
		this.userService = userService;
	}


	@Override
	public void run(ApplicationArguments args) {
		createAdmin();
	}

	public void createAdmin(){
		if( userService.getByLogin("Super_Sys_Admin") == null ){
			User superAdmin = new User(userService.generateId(), "Super_Sys_Admin", "PINE_APPLE2501",
					Set.of(Role.SYS_ADMIN, Role.STAFF, Role.USER));
			userService.save(superAdmin);
			log.info("Created new Super_Sys_Admin");
		}
	}

}
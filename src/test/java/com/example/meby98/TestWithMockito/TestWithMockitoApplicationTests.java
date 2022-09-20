package com.example.meby98.TestWithMockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.meby98.TestWithMockito.repository.UserRepository;
import com.example.meby98.TestWithMockito.repository.RoleRepository;
import com.example.meby98.TestWithMockito.service.UserService;
import com.example.meby98.TestWithMockito.models.*;
import com.example.meby98.TestWithMockito.dto.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class TestWithMockitoApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void saveUserTest() {
		User userDB = new User(1L, "MEBY98", "myexample@gmail.com", new BCryptPasswordEncoder().encode("123"),
				this.setUpRoles());
		UserRequest newUser = new UserRequest("MEBY98", "123", "myexample@gmail.com",
				Arrays.asList("ROLE_ADMIN"));

		when(encoder.encode("123")).thenReturn("123EncoderWithBCrypt");
		when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(new Role(1L, "ROLE_ADMIN"));
		when(userRepository.save(any(User.class))).thenReturn(userDB);

		assertEquals(userDB.getPassword(), this.userService.register(newUser).getPassword());
		assertEquals(userDB, this.userService.register(newUser));
	}

	@Test
	void findByIdTest() {
		User userDB = new User(1L, "MEBY98", "myexample@gmail.com", new BCryptPasswordEncoder().encode("123"),
				this.setUpRoles());
		when(userRepository.findById(1L)).thenReturn(Optional.of(userDB));
		User u = this.userService.findById(1L);
		assertEquals("MEBY98", u.getUsername());
		assertEquals("myexample@gmail.com", u.getEmail());
	}

	private Set<Role> setUpRoles() {
		List<Role> roles = new LinkedList<>();
		roles.add(new Role(1L, "ROLE_ADMIN"));
		return new HashSet<>(roles);
	}

}

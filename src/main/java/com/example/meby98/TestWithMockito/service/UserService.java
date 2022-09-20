package com.example.meby98.TestWithMockito.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.meby98.TestWithMockito.dto.UserRequest;
import com.example.meby98.TestWithMockito.models.Role;
import com.example.meby98.TestWithMockito.models.User;
import com.example.meby98.TestWithMockito.repository.RoleRepository;
import com.example.meby98.TestWithMockito.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(UserRequest newUser) {
        Set<Role> roles = new HashSet<>();
        for (String rol : newUser.getRoles()) {
            roles.add(this.roleRepository.findByName(rol));
        }
        User u = new User(newUser.getUsername(), newUser.getEmail(),
                bCryptPasswordEncoder.encode(newUser.getPassword()), roles);
        return this.userRepository.save(u);
    }

    public User findById(Long id) {
        Optional<User> u = this.userRepository.findById(id);
        if (!u.isPresent()) {
            return null;
        }
        return u.get();
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }
}

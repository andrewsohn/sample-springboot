package kr.sample.spring.services;

import kr.sample.spring.dto.UserDto;
import kr.sample.spring.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

/**
 * Created by andrew on 2017. 1. 6..
 */
public interface UserService extends UserDetailsService {
    Collection<GrantedAuthority> getAuthorities(String username);
    public User readUser(UserDto.Read read);
    public User createUser(UserDto.Create dto);
    public void deleteUser(String username);
    public PasswordEncoder passwordEncoder();
}

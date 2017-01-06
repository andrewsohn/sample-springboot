package kr.sample.spring.services;

import kr.sample.spring.dto.UserDto;
import kr.sample.spring.models.User;
import kr.sample.spring.repositories.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by andrew on 2017. 1. 6..
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(UserDto.Create dto){

        String rawPassword = dto.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);

        dto.setPassword(encodedPassword);

        User user = modelMapper.map(dto, User.class);

        return userDao.create(user);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String username) {
        return null;
    }

    @Override
    public User readUser(UserDto.Read dto) {
        return userDao.getByUserId(dto.getUserid());
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

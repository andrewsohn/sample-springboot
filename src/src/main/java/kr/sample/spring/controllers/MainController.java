package kr.sample.spring.controllers;

import kr.sample.spring.dto.UserDto;
import kr.sample.spring.exceptions.UserException;
import kr.sample.spring.models.User;
import kr.sample.spring.repositories.UserDao;
import kr.sample.spring.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by andrew on 2017. 1. 5..
 */

@Controller
@Slf4j
@RequestMapping(value = {"/", "/login"})//, method = RequestMethod.GET
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/userControl", method = RequestMethod.POST)
    @ResponseBody
    public String validateUser(@RequestBody @Valid UserDto.Read read, BindingResult result) {
        User user;

        try {
            user = userService.readUser(read);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(!encoder.matches(read.getPassword(), user.getPassword())){
                throw new UserException.UserWrongPasswordException(user.getId());
            }

        }
        catch (Exception ex) {
            return "hata";
        }

        session.setAttribute("userId", user.getUserid());
        return user.getUserid();
    }
}

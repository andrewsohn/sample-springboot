package kr.sample.spring.controllers;

import kr.sample.spring.models.User;
import kr.sample.spring.repositories.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by andrew on 2017. 1. 5..
 */

@Controller
@Slf4j
@RequestMapping(value = {"/", "/login"})//, method = RequestMethod.GET
public class MainController {
    @Autowired
    UserDao userDao;
    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/userControl")
    @ResponseBody
    public String validateUser(@RequestParam String lgnUserId, @RequestParam String lgnPass) {
        String userId;
        try {
            User user = userDao.getControlUser(lgnUserId,lgnPass);
            userId = String.valueOf(user.getId());
        }
        catch (Exception ex) {
            return "hata";
        }
        session.setAttribute("userId",userId);
        return userId;
    }
}

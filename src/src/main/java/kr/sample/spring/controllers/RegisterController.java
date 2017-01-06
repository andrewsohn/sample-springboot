package kr.sample.spring.controllers;

import kr.sample.spring.common.ErrorResponse;
import kr.sample.spring.dto.UserDto;
import kr.sample.spring.exceptions.UserException;
import kr.sample.spring.models.User;
import kr.sample.spring.repositories.UserDao;
import kr.sample.spring.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 2017. 1. 6..
 */
@Controller
@Slf4j
@RequestMapping(value = "/register")//, method = RequestMethod.GET
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserDao userDao;
    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value="/userCreate", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody @Valid UserDto.Create create, BindingResult result) throws Exception {

        if(result.hasErrors()){
            return validation(result);
        }

        User user = userService.createUser(create);
        return new ResponseEntity(modelMapper.map(user, UserDto.Response.class), HttpStatus.CREATED);
    }

//    @RequestMapping(value="/userUpdate")
//    @ResponseBody
//    public String updateUser(@RequestParam String lgnUserId, @RequestParam String lgnPass) {
//        String userId;
//        try {
//            User user = userDao.getControlUser(lgnUserId,lgnPass);
//            userId = String.valueOf(user.getId());
//        }
//        catch (Exception ex) {
//            return "hata";
//        }
//        session.setAttribute("userId",userId);
//        return userId;
//    }

    //Excpetion handler
    @ExceptionHandler(UserException.UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBoardNotFoundException(UserException.UserNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] dose not have it!");
        errorResponse.setCode("user.not.found.exception");
        return errorResponse;
    }

    @ExceptionHandler(UserException.UserWrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWrongPasswordException(UserException.UserWrongPasswordException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] wrong password!!");
        errorResponse.setCode("user.wrong.password.exception");
        return errorResponse;
    }

    private ResponseEntity validation(BindingResult result) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Wrong request!");
        errorResponse.setCode("bad.request");
        List<ErrorResponse.FieldError> errorList = new ArrayList<>();
        for (Object object : result.getAllErrors()) {
            if(object instanceof FieldError) {
                kr.sample.spring.common.ErrorResponse.FieldError fe = new kr.sample.spring.common.ErrorResponse.FieldError();
                FieldError fieldError = (FieldError) object;
                fe.setField(fieldError.getField());
                fe.setCode(fieldError.getCode());
                fe.setMessage(fieldError.getDefaultMessage());
                errorList.add(fe);
            }
        }
        errorResponse.setErrors(errorList);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

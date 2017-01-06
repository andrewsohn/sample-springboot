package kr.sample.spring.exceptions;

/**
 * Created by andrew on 2017. 1. 6..
 */
public class UserException {
    public static class UserNotFoundException extends RuntimeException{
        int id;
        public UserNotFoundException(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class UserWrongPasswordException extends RuntimeException {
        int id;
        public UserWrongPasswordException(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
    }
}

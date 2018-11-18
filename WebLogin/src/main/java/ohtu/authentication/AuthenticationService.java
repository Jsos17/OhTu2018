package ohtu.authentication;

import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();

        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }

        String error = invalid(username, password, passwordConfirmation);
        if (!error.equals("")) {
            status.addError(error);
        }

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }

        return status;
    }

    private String invalid(String username, String password, String passwordConfirmation) {
        // validity check of username and password
        if (username.matches("[a-z]{3,}")) {
            for (User user : userDao.listAll()) {
                if (user.getUsername().equals(username)) {
                    return "username exists already";
                }
            }
        } else {
            return "username should have at least 3 characters";
        }

        if (password.length() >= 8) {
            if (!password.equals(passwordConfirmation)) {
                return "password and password confirmation do not match";
            }

            int digits = 0;
            int specials = 0;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    digits++;
                }

                if (!Character.isLetterOrDigit(password.charAt(i)) && !Character.isSpaceChar(password.charAt(i))) {
                    specials++;
                }
            }

            if (digits == 0 && specials == 0) {
                return "password should have at least one digit or special character";
            }
        } else {
            return "password should have at least 8 characters";
        }

        return "";
    }
}

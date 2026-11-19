package com.mycompany.quickchatpoe;


/**
 *
 * @author Studentpackage com.mycompany.part1registration;

 */
public class part1registration {

    public part1registration(java.lang.String firstName, java.lang.String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

/**
 *
 * @author Student
 */
public class registration {
    }
    private String storedUsername;
    private String storedPassword;
    private final String firstName;
    private final String lastName;

    
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    public boolean checkPasswordComplexity(String password) {
        boolean hasLength = password.length() >= 8;
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasLength && hasCapital && hasNumber && hasSpecial;
    }
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("^\\+27\\d{9}$");
    }
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        this.storedUsername = username;
        this.storedPassword = password;
        return "User has been registered successfully.";
    }
    public boolean loginUser(String username, String password) {
        if (storedUsername == null) return false;
        return username.equals(storedUsername) && password.equals(storedPassword);
    }
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    public String getUsernameMessage(String u) {
        if (checkUserName(u)) return "Username successfully captured.";
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }
    public String getPasswordMessage(String p) {
        if (checkPasswordComplexity(p)) return "Password successfully captured.";
        return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    }
    public String getCellMessage(String c) {
        if (checkCellPhoneNumber(c)) return "Cell phone number successfully added.";
        return "Cell phone number is incorrectly formatted or does not contain international code.";
    }
}


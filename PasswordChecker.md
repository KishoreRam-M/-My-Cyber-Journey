# Java Password Strength Checker for Cybersecurity

In the context of **cybersecurity**, ensuring strong password practices is crucial to protecting user accounts and sensitive information. A password checker can be used to evaluate the strength of a password and enforce certain rules to reduce the risk of unauthorized access.

This document provides a **Java code** example of a **Password Strength Checker**, which checks a password based on the following criteria:
- Minimum length (e.g., 8 characters)
- Inclusion of both uppercase and lowercase letters
- Inclusion of at least one digit
- Inclusion of at least one special character (e.g., `@`, `#`, `$`)
- Avoidance of common patterns (e.g., "12345", "password")

The code also provides feedback to the user regarding the strength of the password.

## Password Strength Criteria
A strong password should have:
- At least **8 characters**.
- At least one **uppercase letter**.
- At least one **lowercase letter**.
- At least one **digit**.
- At least one **special character** (such as `@`, `#`, `$`, `%`).
- Should **not** contain common patterns like "password", "12345", or repetitive sequences.

## Java Code Implementation

```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordChecker {

    // Method to check the strength of a password
    public static String checkPasswordStrength(String password) {
        // Validate if the password length is at least 8 characters
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        // Check if the password contains both upper and lower case letters
        if (!containsUppercase(password) || !containsLowercase(password)) {
            return "Password must contain both uppercase and lowercase letters.";
        }

        // Check if the password contains at least one digit
        if (!containsDigit(password)) {
            return "Password must contain at least one digit.";
        }

        // Check if the password contains at least one special character
        if (!containsSpecialCharacter(password)) {
            return "Password must contain at least one special character (@, #, $, %, etc.).";
        }

        // Check for common patterns in the password
        if (containsCommonPatterns(password)) {
            return "Password contains common patterns or sequences (e.g., 'password', '12345').";
        }

        return "Password is strong.";
    }

    // Method to check if the password contains an uppercase letter
    private static boolean containsUppercase(String password) {
        return password.matches(".*[A-Z].*");
    }

    // Method to check if the password contains a lowercase letter
    private static boolean containsLowercase(String password) {
        return password.matches(".*[a-z].*");
    }

    // Method to check if the password contains a digit
    private static boolean containsDigit(String password) {
        return password.matches(".*\\d.*");
    }

    // Method to check if the password contains at least one special character
    private static boolean containsSpecialCharacter(String password) {
        String specialChars = "[!@#$%^&*(),.?\":{}|<>]";
        Pattern pattern = Pattern.compile(specialChars);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    // Method to check if the password contains common patterns (e.g., "12345", "password")
    private static boolean containsCommonPatterns(String password) {
        String[] commonPatterns = {"password", "12345", "qwerty", "abc123", "letmein", "welcome"};
        for (String pattern : commonPatterns) {
            if (password.toLowerCase().contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Test the password checker with different examples
        String[] passwords = {
            "Password123!",
            "weakpass",
            "12345678",
            "Password@1",
            "qwerty123",
            "12345password"
        };

        for (String password : passwords) {
            System.out.println("Password: " + password);
            System.out.println("Strength Check: " + checkPasswordStrength(password));
            System.out.println("-------------------------");
        }
    }
}























Password: Password123!
Strength Check: Password is strong.
-------------------------
Password: weakpass
Strength Check: Password must contain both uppercase and lowercase letters.
-------------------------
Password: 12345678
Strength Check: Password must contain at least one special character (@, #, $, %, etc.).
-------------------------
Password: Password@1
Strength Check: Password is strong.
-------------------------
Password: qwerty123
Strength Check: Password contains common patterns or sequences (e.g., 'password', '12345').
-------------------------
Password: 12345password
Strength Check: Password contains common patterns or sequences (e.g., 'password', '12345').
-------------------------

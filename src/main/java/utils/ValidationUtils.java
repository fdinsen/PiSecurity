/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author gamma
 */
public class ValidationUtils {
    private static int minPasswordLength = 8;
    private static int maxPasswordLength = 64;
    private static int minEmailLength = 3;
    private static int maxEmailLength = 254;
    
    public static String escapeUnsafeCharacters(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
    
    public static boolean isEmailValid(String email) {
        return inputLongerThanMinLength(email, minEmailLength) 
            && inputShorterThanMaxLength(email, maxEmailLength)
            ;
    }
    
    public static boolean isPasswordValid(String password) {
        return inputLongerThanMinLength(password, minPasswordLength) 
            && inputShorterThanMaxLength(password, maxPasswordLength)
            ;
    }
    
    private static boolean inputLongerThanMinLength(String input, int minLength) {
        return input.length() >= minLength;
    }
    
    private static boolean inputShorterThanMaxLength(String input, int maxLength) {
        return input.length() <= maxLength;
    }

    public static int getMinPasswordLength() {
        return minPasswordLength;
    }

    public static int getMaxPasswordLength() {
        return maxPasswordLength;
    }
    
    
}

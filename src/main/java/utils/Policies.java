/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author gamma
 */
public class Policies {

    public static String getPasswordPolicy() {
        return "Password must be between " 
                + ValidationUtils.getMinPasswordLength() 
                + " and " 
                + ValidationUtils.getMaxPasswordLength()
                + " characters.";
    }
}

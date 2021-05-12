/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencies;

/**
 *
 * @author gamma
 */
public interface IEmailConnection {
    public abstract void sendEmail(String email, String username, String url);
}

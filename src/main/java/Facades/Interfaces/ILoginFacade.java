package Facades.Interfaces;

import DTO.LoginDTO;
import DTO.UserDTO;

public interface ILoginFacade {
    public UserDTO verifyCredentials(LoginDTO user);
    public boolean usernameExists(String userName);
    public UserDTO createUser(String username, String email, String password);
    public String createActivationUrl(String requestUrl, String username);
    public void sendActivationEmail(String email, String username, String url);
}

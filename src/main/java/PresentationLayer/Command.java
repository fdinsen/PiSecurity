package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import Models.User;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {

    public Role[] rolesAllowed;
    private static HashMap<String, Command> commands;
    

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("login", new Login(new Role[]{}));
        commands.put("logout", new Logout(new Role[]{}));
        commands.put("register", new Register(new Role[]{}));
        commands.put("resend", new ResendActivationEmail(new Role[]{Role.unverified}));
    }

    public static Command from( HttpServletRequest request ) {
        String targetName = request.getParameter( "target" );
        if ( commands == null ) {
            initCommands();
        }
        return commands.getOrDefault(targetName, new UnknownCommand()); // unknowncommand er default.
    }

    public abstract String execute( HttpServletRequest request, HttpServletResponse response ) 
            throws LoginSampleException;
    
    public Command(Role[] rolesAllowed) {
        this.rolesAllowed = rolesAllowed;
    }
    
    public boolean isUserAllowed(HttpServletRequest request) {
        if(rolesAllowed.length == 0) {
            return true;
        }
        
        Role role = (Role) request.getSession().getAttribute("role");
        return role != null && Arrays.asList(rolesAllowed).contains(role);
    }

}

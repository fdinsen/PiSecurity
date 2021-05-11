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
        commands.put("login", new Login());
        commands.put("logout", new Logout());
        commands.put("register", new Register());
        commands.put("createCategory", new CreateCategory());
        commands.put("register", new Register(new Role[]{}));
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

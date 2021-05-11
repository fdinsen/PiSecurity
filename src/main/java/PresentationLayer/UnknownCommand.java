package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 The purpose of UnknownCommand is to...

 @author kasper
 */
public class UnknownCommand extends Command {

    public UnknownCommand(Role[] rolesAllowed) {
        super(rolesAllowed);
    }
    
    public UnknownCommand() {
        super(new Role[]{});
    }

    @Override
    public String execute( HttpServletRequest request, HttpServletResponse response ) throws LoginSampleException {
        String msg = "Unknown command. Contact IT";
        throw new LoginSampleException( msg );
    }

}

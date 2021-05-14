package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import PresentationLayer.Board.*;
import PresentationLayer.Category.ViewCategories;
import PresentationLayer.Category.CreateCategory;
import PresentationLayer.Category.DeleteCategory;
import PresentationLayer.Category.EditCategory;

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
        commands.put("createCategory", new CreateCategory(new Role[]{}));
        commands.put("viewCategories", new ViewCategories(new Role[]{}));
        commands.put("editCategory", new EditCategory(new Role[]{}));
        commands.put("deleteCategory", new DeleteCategory(new Role[]{}));
        commands.put("getCreateBoard", new GetCreateBoard(new Role[]{}));
        commands.put("createBoard", new CreateBoard(new Role[]{}));
        commands.put("adminViewBoardsForCategory", new AdminViewBoardsForCategory(new Role[]{}));
        commands.put("editBoard", new EditBoard(new Role[]{}));
        commands.put("deleteBoard", new DeleteBoard(new Role[]{}));
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
        String roleStr = (String) request.getSession().getAttribute("role");
        Role role = Role.valueOf(roleStr);
        return role != null && Arrays.asList(rolesAllowed).contains(role);
    }

}

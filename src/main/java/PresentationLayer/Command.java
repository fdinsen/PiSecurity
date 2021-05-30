package PresentationLayer;

import Exceptions.LoginSampleException;
import Models.Role;
import PresentationLayer.Board.*;
import PresentationLayer.Category.ViewCategories;
import PresentationLayer.Category.CreateCategory;
import PresentationLayer.Category.DeleteCategory;
import PresentationLayer.Category.EditCategory;
import PresentationLayer.Forum.GetBoard;
import PresentationLayer.Post.CreatePost;
import PresentationLayer.Thread.CreateThread;
import PresentationLayer.Thread.DeleteThread;

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
        commands.put("createCategory", new CreateCategory(new Role[]{Role.admin}));
        commands.put("viewCategories", new ViewCategories(new Role[]{Role.admin}));
        commands.put("editCategory", new EditCategory(new Role[]{Role.admin}));
        commands.put("deleteCategory", new DeleteCategory(new Role[]{Role.admin}));
        commands.put("getCreateBoard", new GetCreateBoard(new Role[]{Role.admin}));
        commands.put("createBoard", new CreateBoard(new Role[]{Role.admin}));
        commands.put("adminViewBoardsForCategory", new AdminViewBoardsForCategory(new Role[]{Role.admin}));
        commands.put("editBoard", new EditBoard(new Role[]{Role.admin}));
        commands.put("deleteBoard", new DeleteBoard(new Role[]{Role.admin}));
        commands.put("getBoard", new GetBoard(new Role[]{}));
        commands.put("createThread", new CreateThread(new Role[]{Role.user, Role.moderator, Role.admin}));
        commands.put("deleteThread", new DeleteThread(new Role[] {Role.user, Role.moderator, Role.admin}));
        commands.put("uploadProfilePicture", new UploadProfilePicture(new Role[] {Role.user, Role.moderator, Role.admin}));
        commands.put("createPost", new CreatePost(new Role[] {Role.user, Role.moderator, Role.admin}));
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

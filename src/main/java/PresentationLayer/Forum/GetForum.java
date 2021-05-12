package PresentationLayer.Forum;

import Exceptions.DBErrorException;
import Models.Category;
import Models.Role;
import PresentationLayer.Command;
import Service.ForumFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetForum extends Command {

    public GetForum(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Get categories
        try{
            ForumFacade forumFacade  = new ForumFacade();
            List<Category> categories = forumFacade.getCategoriesWithBoards();
            request.setAttribute("categories", categories);
        }catch (DBErrorException e) {
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            request.setAttribute("errMsg", "Something went wrong while getting forum");
        }

        return "index";
    }
}

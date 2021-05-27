package PresentationLayer.Forum;

import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Models.Role;
import PresentationLayer.Command;
import Facades.ForumFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.log4j.Logger;

public class GetForum extends Command {

    public GetForum(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //Get categories
        try{
            ForumFacade forumFacade  = new ForumFacade();
            List<CategoryDTO> categories = forumFacade.getCategoriesWithBoards();
            request.setAttribute("categories", categories);
        }catch (DBErrorException e) {
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get forum");
            request.setAttribute("errMsg", e.getMessage());
        }catch (Exception e){
            Logger.getLogger(this.getClass().getName()).error("Database error, could not get forum");
            request.setAttribute("errMsg", "Something went wrong while getting forum");
        }

        return "index";
    }
}

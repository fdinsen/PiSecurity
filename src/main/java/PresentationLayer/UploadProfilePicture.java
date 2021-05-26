package PresentationLayer;

import Exceptions.DBErrorException;
import Exceptions.UserNotFoundException;
import Facades.UserFacade;
import Models.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import utils.ImageSanitizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

public class UploadProfilePicture extends Command {

    File tmpFile = null;
    Path tmpPath = null;

    public UploadProfilePicture(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if(!isMultipart){
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
            return "profile";
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(
                new File(System.getProperty("java.io.tmpdir")));
        factory.setSizeThreshold(1024 * 1024 * 1);
        factory.setFileCleaningTracker(null);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 100);
        upload.setFileSizeMax(1024 * 1024 * 10);

        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            request.setAttribute("errMsg", e.getMessage());
            return "profile";
        }

        //Only 1 item
        if(items.size() != 1){
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
            return "profile";
        }

        //Gets item
        FileItem item = (FileItem) items.get(0);

        //The item should not be a FormField
        if (item.isFormField()) {
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
            return "profile";
        }



        //Get random name
        String filename = UUID.randomUUID().toString().replace('-', '_') + ".png";

        try {
            // Write a temporary file with uploaded file for sanitizing
            tmpFile = File.createTempFile("uploaded-", null);
            tmpPath = tmpFile.toPath();
            long copiedBytesCount = Files.copy(item.getInputStream(), tmpPath, StandardCopyOption.REPLACE_EXISTING);
            if (copiedBytesCount != item.getSize()) {
                throw new IOException(String.format("Error during stream copy to temporary disk (copied: %s / expected: %s !", copiedBytesCount, item.getSize()));
            }

            // Image sanitizing
            ImageSanitizer imageSanitizer = new ImageSanitizer();
            Boolean isSafe = imageSanitizer.madeSafe(tmpFile);

            if(!isSafe){
                request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
                safelyRemoveFile(tmpPath);
                return "profile";
            }

            InputStream uploadedStream = item.getInputStream();

            OutputStream out = new FileOutputStream(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + filename);

            IOUtils.copy(uploadedStream, out);

            request.setAttribute("msg", "Profile picture uploaded");
        } catch (IOException e) {
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
        }finally {
            safelyRemoveFile(tmpPath);
        }

        //TODO set file permissions

        HttpSession session = request.getSession();
        //update in db
        try {
            String username = (String)session.getAttribute("username");

            UserFacade userFacade = new UserFacade();
            String oldPictureFilename = userFacade.updateProfilePicture(filename, username);

            //Delete old profile picture
            File oldPicture = new File(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + oldPictureFilename);
            FileUtils.forceDelete(oldPicture);
            //safelyRemoveFile(oldPicture.toPath());

        } catch (UserNotFoundException | DBErrorException e) {
            File picture = new File(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + filename);
            safelyRemoveFile(picture.toPath());

            request.setAttribute("errMsg", "Something went wrong while uploading profile picture");
        }catch (IOException e){
            //TODO log when old profile picture cannot be deleted
        }

        session.setAttribute("profilePicture", filename);

        return "profile";
    }

    /**
     * Utility methods to safely remove a file
     *
     * @param p file to remove
     */
    private static void safelyRemoveFile(Path p) {
        try {
            if (p != null) {
                // Remove temporary file

                if (!Files.deleteIfExists(p)) {
                    // If remove fail then overwrite content to sanitize it
                    Files.write(p, "-".getBytes("utf8"), StandardOpenOption.CREATE);
                }
            }
        } catch (Exception e) {
            //TODO LOG
        }
    }
}

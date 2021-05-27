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
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import utils.ImageSanitizer;
import utils.ValidationUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UploadProfilePicture extends Command {

    File newFile = null;
    File oldPicture = null;
    File tmpFile = null;
    Path tmpPath = null;

    public UploadProfilePicture(Role[] rolesAllowed) {
        super(rolesAllowed);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if(!isMultipart){
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture 1");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - request is not a multipart upload request");
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
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - Something went wrong while parsing request, error: " + e.getMessage());
            return "profile";
        }

        //Only 1 item
        if(items.size() != 1){
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture 2");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  +  " - request does not contain exactly 1 form item, items: " + items.size());
            return "profile";
        }

        //Gets item
        FileItem item = (FileItem) items.get(0);

        //The item should not be a FormField
        if (item.isFormField()) {
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture 3");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - the FileItem is a form field");
            return "profile";
        }

        //Checks extension
        ValidationUtils validationUtils = new ValidationUtils();
        if(!validationUtils.isPNGExtension(item.getName())){
            request.setAttribute("errMsg", "File must be of type png");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - uploaded file did not pass isPNGExtension validation, filename: " + item.getName());
            return "profile";
        }

        //Checks item contentType
        if(!item.getContentType().equals("image/png")){
            request.setAttribute("errMsg", "File must be of type png");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - Content type is not image/png, contentType: " + item.getContentType());
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
                request.setAttribute("errMsg", "Not a valid png file");
                safelyRemoveFile(tmpPath);

                Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - uploaded a file that did not pass the imageSanitizer");
                return "profile";
            }

            //Move sanitized file to upload folder
            newFile = new File(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + filename);

            Files.move(tmpFile.toPath(), newFile.toPath());

            request.setAttribute("msg", "Profile picture uploaded");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - Something went wrong while validating and creating uploaded file, error: " + e.getMessage());
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture, error: " + e.getMessage());
        }
        finally {
            safelyRemoveFile(tmpPath);
        }

        //set file permissions
        //read write only
        //TODO test more
        try{
            setPermission(newFile);
        } catch (Exception e) {
            safelyRemoveFile(newFile.toPath());
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture 4");
            Logger.getLogger(this.getClass().getName()).error("IP: " + request.getRemoteAddr()  + " - Something went wrong while setting file permissions, error: " + e.getMessage());
            return "profile";
        }



        HttpSession session = request.getSession();
        //update in db
        try {
            String username = (String)session.getAttribute("username");

            UserFacade userFacade = new UserFacade();
            String oldPictureFilename = userFacade.updateProfilePicture(filename, username);

            //Delete old profile picture
            oldPicture = new File(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + oldPictureFilename);
            FileUtils.forceDelete(oldPicture);
            //safelyRemoveFile(oldPicture.toPath());

        } catch (UserNotFoundException | DBErrorException e) {
            File picture = new File(System.getProperty("user.dir")  + File.separator +  "uploads" +  File.separator + filename);
            safelyRemoveFile(picture.toPath());
            Logger.getLogger(this.getClass().getName()).warn("IP: " + request.getRemoteAddr()  + " - Something went updating profile picture in db, error: " + e.getMessage());
            request.setAttribute("errMsg", "Something went wrong while uploading profile picture 5");
        }catch (IOException e){
            Logger.getLogger(this.getClass().getName()).warn("IP: " + request.getRemoteAddr()  + " - old profile picture could not be deleted, filename: " + oldPicture.getName());
        }

        session.setAttribute("profilePicture", filename);

        return "profile";
    }

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

    private void setPermission(File file) throws IOException{
        Set<PosixFilePermission> perms = new HashSet<>();
        if(SystemUtils.IS_OS_WINDOWS) {
            file.setReadable(true, true);
            file.setWritable(true, true);
            file.setExecutable(false);
        }else{
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);

            Files.setPosixFilePermissions(file.toPath(), perms);
        }
    }
}
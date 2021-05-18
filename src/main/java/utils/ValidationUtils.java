/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DTO.BoardDTO;
import DTO.CategoryDTO;
import Exceptions.DBErrorException;
import Exceptions.InvalidInputException;
import Models.Board;
import Models.Category;
import Persistence.CategoryDaoImpl;
import Facades.BoardFacade;
import Facades.CategoryFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.persistence.EntityManager;

/**
 *
 * @author gamma
 */
public class ValidationUtils {
    private static int minPasswordLength = 8;
    private static int maxPasswordLength = 64;
    private static int minEmailLength = 3;
    private static int maxEmailLength = 254;
    
    public static String escapeUnsafeCharacters(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
    
    public static boolean isEmailValid(String email) {
        return inputLongerThanMinLength(email, minEmailLength) 
            && inputShorterThanMaxLength(email, maxEmailLength)
            ;
    }
    
    public static boolean isPasswordValid(String password) {
        return inputLongerThanMinLength(password, minPasswordLength) 
            && inputShorterThanMaxLength(password, maxPasswordLength)
            ;
    }
    
    private static boolean inputLongerThanMinLength(String input, int minLength) {
        return input.length() >= minLength;
    }
    
    private static boolean inputShorterThanMaxLength(String input, int maxLength) {
        return input.length() <= maxLength;
    }

    public static int getMinPasswordLength() {
        return minPasswordLength;
    }

    public static int getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public static void categoryNameValidation(String catName) throws InvalidInputException {
        if(StringUtils.isBlank(catName)){
            throw new InvalidInputException("Category name must be set and not be only whitespace");
        }

        if (!inputShorterThanMaxLength(catName, 50) || !inputLongerThanMinLength(catName, 4) ) {
            throw new InvalidInputException("Category name must be at between 4-50 chars");
        }
    }

    public static CategoryDTO categoryIdStringValidation(String catID) throws InvalidInputException {
        if(StringUtils.isBlank(catID)){
            throw new InvalidInputException("Category id must be set and not be only whitespace");
        }

        int categoryId;
        try{
            categoryId = Integer.parseInt(catID);
        }catch(Exception e){
            throw new InvalidInputException("Category ID must be a integer");
        }

        //Get category
        try{
            CategoryFacade categoryFacade  = new CategoryFacade();
            CategoryDTO categoryDTO = categoryFacade.getCategoryFromID(categoryId);
            return categoryDTO;
        }catch (DBErrorException e) {
            throw new InvalidInputException(e.getMessage());
        }catch (Exception e){
            throw new InvalidInputException("Something went wrong while getting category from DB");
        }
    }

    public static void boardNameValidation(String boardName) throws InvalidInputException {
        if(StringUtils.isBlank(boardName)){
            throw new InvalidInputException("Board name must be set and not be only whitespace");
        }

        if (!inputShorterThanMaxLength(boardName, 50) || !inputLongerThanMinLength(boardName, 4) ) {
            throw new InvalidInputException("Board name must be at between 4-50 chars");
        }
    }

    public static BoardDTO boardIdStringValidation(String boardIDString) throws InvalidInputException {
        if(StringUtils.isBlank(boardIDString)){
            throw new InvalidInputException("Board id must be set and not be only whitespace");
        }

        int boardIdInt;
        try{
            boardIdInt = Integer.parseInt(boardIDString);
        }catch(Exception e){
            throw new InvalidInputException("Board ID must be a integer");
        }

        //Get board
        try{
            BoardFacade boardFacade  = new BoardFacade();
            BoardDTO board = boardFacade.getBoardFromID(boardIdInt);
            return board;
        }catch (DBErrorException e) {
            throw new InvalidInputException(e.getMessage());
        }catch (Exception e){
            throw new InvalidInputException("Something went wrong while getting board from DB");
        }
    }

    public static void boardDescriptionValidation(String boardDescription) throws InvalidInputException {
        if(StringUtils.isBlank(boardDescription)){
            throw new InvalidInputException("Board description must be set and not be only whitespace");
        }

        if (!inputShorterThanMaxLength(boardDescription, 150) || !inputLongerThanMinLength(boardDescription, 10) ) {
            throw new InvalidInputException("Board description must be at between 10-150 chars");
        }
    }

    public static void threadNameValidation(String threadName) throws InvalidInputException {
        if(StringUtils.isBlank(threadName)){
            throw new InvalidInputException("Thread name must be set and not be only whitespace");
        }

        if (!inputShorterThanMaxLength(threadName, 50) || !inputLongerThanMinLength(threadName, 4) ) {
            throw new InvalidInputException("Thread name must be at between 4-50 chars");
        }
    }

    public static String threadTextValidation(String text) throws InvalidInputException {
        if(StringUtils.isBlank(text)){
            throw new InvalidInputException("Text name must be set and not be only whitespace");
        }

        if (!inputShorterThanMaxLength(text, 10000) || !inputLongerThanMinLength(text, 30) ) {
            throw new InvalidInputException("Thread text must be at between 30-10000 chars");
        }

        return Jsoup.clean(text, Whitelist.basicWithImages());
    }
}

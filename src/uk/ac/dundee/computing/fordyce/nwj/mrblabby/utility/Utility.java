/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.utility;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Neil
 */
public class Utility {

    /**
     * Removes / prefix from parameter
     *
     * Removes /json suffix
     *
     * @param idParameter
     * @return idParameter with / and /json removed
     */
    public static String cleanParameter(String idParameter) {
        if (idParameter == null) {
            return "";
        }
        if (idParameter.endsWith("/json")) {
            idParameter = idParameter.replaceAll("/json$", "");
        }
        return idParameter.replaceAll("[/]", "");
    }

    /**
     * Finds out if a request object specifies a JSON request
     *
     * @param pathInfo
     * @return
     */
    public static boolean isJson(HttpServletRequest request) {
        if (request.getPathInfo() == null) {
            return false;
        }
        return request.getPathInfo().endsWith("/json");
    }

    /**
     * Determines if id is numeric
     *
     * @param idParameter
     * @return true if the id is numeric
     */
    public static boolean isNumeric(String idParameter) {
        if (idParameter == null) {
            return false;
        }
        return idParameter.matches("\\d+");
    }

    /**
     * Checks if an email address is valid Based on answer from
     * http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     *
     * @return true if email address is valid, false otherwise
     */
    public static boolean isEmail(String idParameter) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(idParameter);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
}

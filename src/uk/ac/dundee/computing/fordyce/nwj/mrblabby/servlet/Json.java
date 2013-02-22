/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.servlet;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/json", "/json/*"})
public class Json extends HttpServlet {

    /**
     * Turns object at the data attribute into a String of JSON
     * 
     * @param request
     * @param response 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Gson gson = new Gson();

        Object data = request.getAttribute("data");
        String json = gson.toJson(data);
        
        try {
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (IOException ex) {
            Logger.getLogger(Json.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

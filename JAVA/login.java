/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osl.servlets;
import com.sun.media.sound.InvalidDataException;
import osl.pojo.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Dishesh
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ret_url=request.getParameter("ret_url");
        
//        try
//        {if(request.getParameter("email").isEmpty())
//        {throw new Exception("ENTER VALID EMAIL");}
//        else if(request.getParameter("pass").isEmpty())
//            {throw new Exception("PASSWORD EMPTY");}
//            }
//        
//        catch(Exception E){ System.out.print(E);System.err.println("EXception at Login SErvlet");
//        
//        response.getWriter().print(E);
//        response.sendRedirect("http://localhost:22844/OneStopLead/OSL/index.html");
//        
//        }
        
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");// populates the data of the
                                            // configuration file

        // creating seession factory object
        SessionFactory factory = cfg.buildSessionFactory();

        // creating session object
        Session session = factory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();
        try{
        Query query = session.createQuery("from user where email='"+request.getParameter("email")+"'");
        List obj = query.list(); 
        if(obj.isEmpty()){response.sendError(100, "Account Doesnot Exist");}
         for (Iterator iterator = 
                           obj.iterator(); iterator.hasNext();){
            user us_obj = (user) iterator.next(); 
        
        if(request.getParameter("pass").equals(us_obj.getPassword()))
        {
            response.addCookie(new Cookie("user",us_obj.getF_Name()));
            HttpSession userSession=request.getSession();
            
            userSession.setAttribute("user-name", us_obj.getF_Name()+us_obj.getL_Name());
            userSession.setAttribute("user-id", us_obj.getUserId());
            userSession.setAttribute("user-gender", us_obj.getGender());
            userSession.setAttribute("user-email", us_obj.getEmail());
            userSession.setAttribute("user-contact", us_obj.getContact());
            userSession.setAttribute("user-city", us_obj.getCity());
            userSession.setAttribute("user-DOB", us_obj.getDOB());
            
            response.sendRedirect(ret_url);
                    
                   
        }else
        {
            response.setHeader("Unauthorizesd", "RETRY");
            response.sendRedirect("page_401.html");
        }
        System.out.println(us_obj.getUserId()+"<>"+us_obj.getF_Name());
         }}catch(IOException io){System.out.println("IOEXCEPTION @"+io);} 
             t.commit();
        session.close();
        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

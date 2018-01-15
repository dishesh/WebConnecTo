/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osl.servlets;
import osl.pojo.user;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Dishesh
 */
@WebServlet(name = "signup", urlPatterns = {"/signup"})
public class signup extends HttpServlet {

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
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");// populates the data of the
                                            // configuration file
        user obj=new user();
       String date=(request.getParameter("DOB"));
       java.sql.Date dat=Date.valueOf(request.getParameter("DOB"));
       
        obj.setF_Name(request.getParameter("f_Name"));
        obj.setL_Name(request.getParameter("l_Name"));
        obj.setGender(request.getParameter("gender"));
        obj.setDOB(dat);
        obj.setEmail(request.getParameter("email"));
        obj.setPassword(request.getParameter("password"));
        
        obj.setContact(request.getParameter("contact"));
        obj.setCity(request.getParameter("city"));
        // creating seession factory object
        SessionFactory factory = cfg.buildSessionFactory();
        // creating session object
        Session session = factory.openSession();
        // creating transaction object
        Transaction t = session.beginTransaction();
        try{
            session.save(obj);
            
            session.getTransaction().commit();
            System.out.println("Inserted Row");
            response.addHeader("reciept", "SuccessInsert");
            response.setHeader("sts", "inserted");
            response.sendRedirect("OSL/index.html#login");
            
             try{
//                String mssgstr=gson.toJson(s);
                TestEmailSender sub_email;
                sub_email=new TestEmailSender();
                sub_email.setMailTo(request.getParameter("email"));
                sub_email.setSubject("OneStopLead, Account Created");
               sub_email.setBodyMessage("Welcome TO <b>OneStopLead</b> Famlily<br/> "
                       +"Click <a href=http://localhost:22844/OneStopLead/VerifyEmail?email="+request.getParameter("email")+"> Here</a>"                                         + " We Provide Best of Lead Services, Create and HostLanding Pages and so more"+
                        "Find Us @ <a href=#>OneStopLead</a>");
                //sub_email.setBodyMessage("WELCOME==<a href=http://localhost:22844/OneStopLead/Activate?str="+gson.toJson(s.getEmail())+">HERE</a>");
                sub_email.main();
            
            
            }catch(MessagingException subs_mailEx){System.out.println("ERROR IN MAIL"+subs_mailEx);}
        //HibernateUtil.shutdown();
        //Query query = session.createQuery("from user where email='"+request.getParameter("email")+"'");
        
        
        }catch(Exception e){System.out.println("Exception @ Insert"+e);}
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

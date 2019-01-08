/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Ejb.ProductBean;
import Ejb.ProductSpecBean;
import Ejb.TempBean;
import PosClasses.ProductSpecDetails;
import PosClasses.TempDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romelia Milascon
 */
@WebServlet(name = "AddSale", urlPatterns = {"/AddSale"})
public class AddSale extends HttpServlet {

    @Inject
    private ProductBean productBean;
    
    @Inject
    private ProductSpecBean productSpecBean;
    
    @Inject
    TempBean temporarBean;
    
    //List<ProductSpecDetails> productSpecList = new ArrayList<ProductSpecDetails>();
 
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddSale</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSale at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
       
        request.getRequestDispatcher("/WEB-INF/Pages/Sale.jsp").forward(request, response);
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
        
        String barcode = request.getParameter("barCode");
        ProductSpecDetails prodSpecDetails = productSpecBean.findByBarcode(barcode);
        
        Integer quantity = 1;
        //if(temporarBean.findByName(prodSpecDetails.getProdName())!=null)
            temporarBean.createTemp(prodSpecDetails.getProdName(), prodSpecDetails.getDescription(), prodSpecDetails.getPrice(),quantity);
//        else
//        {
//            Integer id=temporarBean.findByName(prodSpecDetails.getProdName()).getId();
//            Integer q=quantity+temporarBean.findByName(prodSpecDetails.getProdName()).getQuantity();
//            temporarBean.updateTemp(id,q);
//        }
        
        List<TempDetails> temporarProducts = temporarBean.getAllTemporars();
        Double total=temporarBean.getTotal();
        request.setAttribute("temporarProducts", temporarProducts);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/WEB-INF/Pages/Sale.jsp").forward(request, response);
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
